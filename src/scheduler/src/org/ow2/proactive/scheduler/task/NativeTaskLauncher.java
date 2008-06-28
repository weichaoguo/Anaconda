/*
 * ################################################################
 *
 * ProActive: The Java(TM) library for Parallel, Distributed,
 *            Concurrent computing with Security and Mobility
 *
 * Copyright (C) 1997-2007 INRIA/University of Nice-Sophia Antipolis
 * Contact: proactive@objectweb.org
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version
 * 2 of the License, or any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA
 *
 *  Initial developer(s):               The ProActive Team
 *                        http://proactive.inria.fr/team_members.htm
 *  Contributor(s):
 *
 * ################################################################
 */
package org.ow2.proactive.scheduler.task;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.objectweb.proactive.ActiveObjectCreationException;
import org.objectweb.proactive.core.node.NodeException;
import org.ow2.proactive.resourcemanager.common.scripting.GenerationScript;
import org.ow2.proactive.resourcemanager.common.scripting.Script;
import org.ow2.proactive.resourcemanager.common.scripting.ScriptHandler;
import org.ow2.proactive.resourcemanager.common.scripting.ScriptLoader;
import org.ow2.proactive.resourcemanager.common.scripting.ScriptResult;
import org.ow2.proactive.scheduler.common.exception.UserException;
import org.ow2.proactive.scheduler.common.task.Log4JTaskLogs;
import org.ow2.proactive.scheduler.common.task.TaskId;
import org.ow2.proactive.scheduler.common.task.TaskLogs;
import org.ow2.proactive.scheduler.common.task.TaskResult;
import org.ow2.proactive.scheduler.core.SchedulerCore;
import org.ow2.proactive.scheduler.util.process.ProcessTreeKiller;


/**
 * This launcher is the class that will launch a native class.
 *
 * @author The ProActive Team
 * @version 3.9, Jul 10, 2007
 * @since ProActive 3.9
 */
public class NativeTaskLauncher extends TaskLauncher {

    /**
     * Environment variable exported to the the process
     * used for kill the task   
     */
    private static String COOKIE_ENV = "PROACTIVE_COOKIE";

    /**
     * random value of the ProActive cookie environment variable 
     */
    private String cookie_value;

    /**
     * ProActive Empty Constructor
     */
    public NativeTaskLauncher() {
    }

    /**
     * Constructor of the native task launcher.
     * CONSTRUCTOR USED BY THE SCHEDULER CORE : plz do not remove.
     *
     * @param taskId the task identification.
     */
    public NativeTaskLauncher(TaskId taskId) {
        super(taskId);
        cookie_value = ProcessTreeKiller.createCookie();
    }

    /**
     * Constructor of the native task launcher.
     * CONSTRUCTOR USED BY THE SCHEDULER CORE : plz do not remove.
     *
     * @param taskId the task identification.
     * @param pre the script executed before the task.
     */
    public NativeTaskLauncher(TaskId taskId, Script<?> pre) {
        super(taskId, pre);
        cookie_value = ProcessTreeKiller.createCookie();
    }

    /**
     * Execute the user task as an active object.
     *
     * @param core The scheduler core to be notify
     * @param executableContainer contains the task to execute
     * @param results the possible results from parent tasks.(if task flow)
     * @return a task result representing the result of this task execution.
     */
    @SuppressWarnings("unchecked")
    public TaskResult doTask(SchedulerCore core, ExecutableContainer executableContainer,
            TaskResult... results) {
        try {
            if (pre != null) {
                this.executePreScript(getNodes().get(0));
            }

            this.currentExecutable = executableContainer.getExecutable();
            NativeExecutable toBeLaunched = (NativeExecutable) this.currentExecutable;

            //launch generation script
            if (toBeLaunched.getGenerationScript() != null) {
                String preScriptDefinedCommand = this.executeGenerationScript(toBeLaunched
                        .getGenerationScript());

                // if preScriptDefinedCommand is not null, a new command 
                // has been defined by the generation script
                if ((preScriptDefinedCommand != null) &&
                    (!GenerationScript.DEFAULT_COMMAND_VALUE.equals(preScriptDefinedCommand))) {
                    // the command is set
                    toBeLaunched.setCommand(preScriptDefinedCommand);
                }
            }

            // set envp
            toBeLaunched.setEnvp(this.buildNativeExecEnvVars());

            //set modelEnv Var for kill action
            HashMap<String, String> modelEnvVar = new HashMap<String, String>();
            modelEnvVar.put(COOKIE_ENV, cookie_value);
            toBeLaunched.setModelEnvVar(modelEnvVar);

            if (isWallTime())
                scheduleTimer();

            //launch task
            Object userResult = toBeLaunched.execute(results);

            //logBuffer is filled up
            TaskLogs taskLogs = new Log4JTaskLogs(this.logBuffer.getBuffer());
            TaskResult result = new TaskResultImpl(taskId, userResult, taskLogs);

            //return result
            return result;
        } catch (Throwable ex) {
            // exceptions are always handled at scheduler core level
            return new TaskResultImpl(taskId, ex, new Log4JTaskLogs(this.logBuffer.getBuffer()));
        } finally {
            if (isWallTime())
                cancelTimer();
            this.finalizeTask(core);
        }
    }

    /**
     * Execute the generationScript on the default node
     * @throws ActiveObjectCreationException if the script handler cannot be created
     * @throws NodeException if the script handler cannot be created
     * @throws UserException if an error occurred during the execution of the script
     * @return the value of the variable GenerationScript.COMMAND_NAME after the script evaluation.
     */
    protected String executeGenerationScript(GenerationScript script) throws ActiveObjectCreationException,
            NodeException, UserException {
        ScriptHandler handler = ScriptLoader.createHandler(getNodes().get(0));
        ScriptResult<String> res = handler.handle(script);

        if (res.errorOccured()) {
            System.err.println("Error on command generation Script occured : ");
            res.getException().printStackTrace();
            throw new UserException("Command generation script execution has failed on the current node");
        }

        return res.getResult();
    }

    /**
     * Build environment variables for the native executable to launch : the task's environment vairables
     * (task name job name, job id, task id...), system environment variables of the JVM, 
     * and the cookie environment variable used by ProcessTreeKiller
     * @return the envp array for scheduler related variables, i.e. {"VAR1_NAME=value1","VAR2_NAME=value2",...}
     */
    private String[] buildNativeExecEnvVars() {

        Map<String, String> taskEnvVariables = new Hashtable<String, String>(4);

        taskEnvVariables.put(SchedulerVars.JAVAENV_JOB_ID_VARNAME.toString(), System
                .getProperty(SchedulerVars.JAVAENV_JOB_ID_VARNAME.toString()));
        taskEnvVariables.put(SchedulerVars.JAVAENV_JOB_NAME_VARNAME.toString(), System
                .getProperty(SchedulerVars.JAVAENV_JOB_NAME_VARNAME.toString()));
        taskEnvVariables.put(SchedulerVars.JAVAENV_TASK_ID_VARNAME.toString(), System
                .getProperty(SchedulerVars.JAVAENV_TASK_ID_VARNAME.toString()));
        taskEnvVariables.put(SchedulerVars.JAVAENV_TASK_NAME_VARNAME.toString(), System
                .getProperty(SchedulerVars.JAVAENV_TASK_NAME_VARNAME.toString()));

        Map<String, String> systemEnvVariables = System.getenv();

        String[] returnTab = new String[taskEnvVariables.size() + systemEnvVariables.size() + 1];

        //first we add to the returnTab the task environment variables
        int i = 0;
        for (Map.Entry<String, String> entry : taskEnvVariables.entrySet()) {
            String name = entry.getKey();
            String value = entry.getValue();
            returnTab[i++] = NativeTaskLauncher.convertJavaenvNameToSysenvName("" + name + "=" + value);
        }

        //after we add to the returnTab the system environment variables
        for (Map.Entry<String, String> entry : systemEnvVariables.entrySet()) {
            String name = entry.getKey();
            String value = entry.getValue();
            returnTab[i++] = "" + name + "=" + value;
        }

        //then the cookie used by ProcessTreeKiller
        returnTab[i] = COOKIE_ENV + "=" + cookie_value;
        return returnTab;
    }

    public static String convertJavaenvNameToSysenvName(String javaenvName) {
        return javaenvName.toUpperCase().replace('.', '_');
    }

}
