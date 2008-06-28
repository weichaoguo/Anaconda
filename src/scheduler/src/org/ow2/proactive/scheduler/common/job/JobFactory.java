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
package org.ow2.proactive.scheduler.common.job;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Map.Entry;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.iso_relax.verifier.Schema;
import org.iso_relax.verifier.Verifier;
import org.iso_relax.verifier.VerifierConfigurationException;
import org.iso_relax.verifier.VerifierFactory;
import org.ow2.proactive.resourcemanager.common.scripting.GenerationScript;
import org.ow2.proactive.resourcemanager.common.scripting.InvalidScriptException;
import org.ow2.proactive.resourcemanager.common.scripting.Script;
import org.ow2.proactive.resourcemanager.common.scripting.SelectionScript;
import org.ow2.proactive.resourcemanager.common.scripting.SimpleScript;
import org.ow2.proactive.scheduler.common.exception.JobCreationException;
import org.ow2.proactive.scheduler.common.exception.UserException;
import org.ow2.proactive.scheduler.common.scheduler.Tools;
import org.ow2.proactive.scheduler.common.task.JavaTask;
import org.ow2.proactive.scheduler.common.task.NativeTask;
import org.ow2.proactive.scheduler.common.task.ProActiveTask;
import org.ow2.proactive.scheduler.common.task.RestartMode;
import org.ow2.proactive.scheduler.common.task.Task;
import org.ow2.proactive.scheduler.task.ForkEnvironment;
import org.ow2.proactive.scheduler.util.classloading.JarUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


public class JobFactory {
    public static final String SCHEMA_LOCATION = "/org/ow2/proactive/scheduler/common/xml/schemas/jobdescriptor/0.91/schedulerjob.rng";
    public static final String STYLESHEET_LOCATION = "/org/ow2/proactive/scheduler/common/xml/stylesheets/variables.xsl";
    public static final String JOB_NAMESPACE = "urn:proactive:jobdescriptor:0.91";
    public static final String JOB_PREFIX = "js";

    //JOBS
    private static final String JOB_TAG = "job";
    private static final String JOB_TASKFLOW = "taskFlow";
    private static final String JOB_PROACTIVE = "proActive";
    private static final String JOB_ATTRIBUTE_PRIORITY = "@priority";
    private static final String JOB_ATTRIBUTE_CANCELONERROR = "@cancelOnError";
    private static final String JOB_ATTRIBUTE_PROJECTNAME = "@projectName";
    private static final String JOB_ATTRIBUTE_LOGFILE = "@logFile";
    //COMMON
    private static final String ATTRIBUTE_ID = "@id";
    private static final String TAG_DESCRIPTION = "description";
    private static final String GENERIC_INFORMATION = "genericInformation/info";
    private static final String GENERIC_INFO_ATTRIBUTE_NAME = "@name";
    private static final String GENERIC_INFO_ATTRIBUTE_VALUE = "@value";
    //TASKS
    private static final String TASK_TAG = "task";
    private static final String JAVA_EXECUTABLE = "javaExecutable";
    private static final String NATIVE_EXECUTABLE = "nativeExecutable";
    private static final String PROACTIVE_EXECUTABLE = "proActiveExecutable";
    private static final String TASK_DEPENDENCES_REF = "depends/task/@ref";
    private static final String TASK_ATTRIBUTE_RESULTPREVIEW = "@resultPreviewClass";
    private static final String TASK_ATTRIBUTE_PRECIOUSRESULT = "@preciousResult";
    private static final String TASK_ATTRIBUTE_TASKRETRIES = "@retries";
    private static final String TASK_ATTRIBUTE_RESTARTONERROR = "@restartOnError";
    private static final String TASK_ATTRIBUTE_CLASSNAME = "@class";
    private static final String TASK_TAG_PARAMETERS = "parameters/parameter";
    private static final String TASK_ATTRIBUTE_NEEDEDNODES = "@neededNodes";
    private static final String TASK_ATTRIBUTE_WALLTIME = "@walltime";
    private static final String TASK_ATTRIBUTE_FORK = "@fork";
    //SCRIPTS
    private static final String TASK_TAG_SELECTION = "selection";
    private static final String TASK_TAG_PRE = "pre";
    private static final String TASK_TAG_POST = "post";
    private static final String TASK_TAG_SCRIPT = "script";
    private static final String SCRIPT_STATICCOMMAND = "staticCommand";
    private static final String SCRIPT_DYNAMICCOMMAND = "dynamicCommand";
    private static final String SCRIPT_ATTRIBUTE_VALUE = "@value";
    private static final String SCRIPT_TAG_ARGUMENTS = "arguments/argument";
    private static final String SCRIPT_TAG_GENERATION = "generation";
    private static final String SCRIPT_TAG_FILE = "file";
    private static final String SCRIPT_TAG_CODE = "code";
    private static final String SCRIPT_ATTRIBUTE_URL = "@url";
    private static final String SCRIPT_ATTRIBUTE_PATH = "@path";
    private static final String SCRIPT_ATTRIBUTE_LANGUAGE = "@language";
    private static final String SCRIPT_ATTRIBUTE_TYPE = "@type";
    //CLASSPATH
    private static final String CP_TAG_CLASSPATHES = "jobClasspath/pathElement";
    private static final String CP_ATTRIBUTE_PATH = "@path";
    //FORK ENVIRONMENT
    private static final String FORK_TAG_ENVIRONMENT = "forkEnvironment";
    private static final String FORK_ATTRIBUTE_JAVAHOME = "@javaHome";
    private static final String FORK_ATTRIBUTE_JVMPARAMETERS = "@jvmParameters";

    private static XPath xpath;

    /**
     * Singleton Pattern
     */
    private static JobFactory factory = null;

    private JobFactory() {
        xpath = XPathFactory.newInstance().newXPath();
        xpath.setNamespaceContext(new SchedulerNamespaceContext());
    }

    public static JobFactory getFactory() {
        if (factory == null) {
            factory = new JobFactory();
        }
        return factory;
    }

    /**
     * Creates a job using the given job descriptor
     * 
     * @param filePath
     *            the path to a job descriptor
     * @return a Job instance
     * @throws JobCreationException
     */
    public Job createJob(String filePath) throws JobCreationException {
        Job job = null;

        try {
            File f = new File(filePath);
            if (!f.exists()) {
                throw new FileNotFoundException("This file has not been found : " + filePath);
            }
            validate(filePath);
            Node rootNode = transformVariablesAndGetDOM(new FileInputStream(f));
            job = createJob(rootNode);
        } catch (Exception e) {
            e.printStackTrace();
            throw new JobCreationException("Exception occured during Job creation", e);

        }
        return job;
    }

    /**
     * Validate the given job descriptor using the internal RELAX_NG Schema
     * 
     * @param filePath
     */
    private void validate(String filePath) throws URISyntaxException, VerifierConfigurationException,
            SAXException, IOException {
        // We use sun multi validator (msv)
        VerifierFactory vfactory = new com.sun.msv.verifier.jarv.TheFactoryImpl();

        InputStream schemaStream = this.getClass().getResourceAsStream(SCHEMA_LOCATION);
        Schema schema = vfactory.compileSchema(schemaStream);
        Verifier verifier = schema.newVerifier();
        ValidatingErrorHandler veh = new ValidatingErrorHandler();
        verifier.setErrorHandler(veh);
        verifier.verify(filePath);
        if (veh.mistakes > 0) {
            System.err.println(veh.mistakes + " mistakes.");
            throw new SAXException(veh.mistakesStack.toString());
        }
    }

    /**
     * Parse the given octet stream to a XML DOM, and transform variable
     * definitions using a stylesheet
     * 
     * @param input
     * @return
     */
    private Node transformVariablesAndGetDOM(InputStream input) throws ParserConfigurationException,
            SAXException, IOException {
        // create a new parser
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        factory.setNamespaceAware(true);
        DocumentBuilder db = factory.newDocumentBuilder();

        Document docSource = db.parse(input);

        System.setProperty("javax.xml.transform.TransformerFactory", "net.sf.saxon.TransformerFactoryImpl");
        DOMSource domSource = new DOMSource(docSource);
        TransformerFactory tfactory = TransformerFactory.newInstance();

        Source stylesheetSource = new StreamSource(this.getClass().getResourceAsStream(STYLESHEET_LOCATION));
        Transformer transformer = null;
        try {
            transformer = tfactory.newTransformer(stylesheetSource);
        } catch (TransformerConfigurationException e1) {
            e1.printStackTrace();
        }
        DOMResult result = new DOMResult();

        try {
            transformer.transform(domSource, result);
        } catch (TransformerException e1) {
            e1.printStackTrace();
        }
        return result.getNode();
    }

    @SuppressWarnings("unchecked")
    private Job createJob(Node rootNode) throws XPathExpressionException, InvalidScriptException,
            SAXException, ClassNotFoundException, IOException, UserException {
        Job job = null;

        // JOB
        XPathExpression exp = xpath.compile(addPrefixes("/" + JOB_TAG));

        Node jobNode = (Node) exp.evaluate(rootNode, XPathConstants.NODE);

        JobType jt = null;

        // JOB TYPE
        Node tfNode = (Node) xpath.evaluate(addPrefixes(JOB_TASKFLOW), jobNode, XPathConstants.NODE);
        if (tfNode != null) {
            jt = JobType.TASKSFLOW;
        }
        Node paNode = (Node) xpath.evaluate(addPrefixes(JOB_PROACTIVE), jobNode, XPathConstants.NODE);
        if (paNode != null) {
            jt = JobType.PROACTIVE;
        }
        if (jt == null) {
            throw new SAXException("Invalid XML : Job must have a valid type");
        }

        switch (jt) {
            case PROACTIVE:
                job = new ProActiveJob();
                break;
            //            case PARAMETER_SWEEPING: TODO
            //                job = new ParameterSweepingJob();
            //                break;
            default:
                job = new TaskFlowJob();
        }
        // JOB NAME
        job.setName((String) xpath.evaluate(ATTRIBUTE_ID, jobNode, XPathConstants.STRING));
        System.out.println("Job : " + job.getName());
        // JOB PRIORITY
        String prio = xpath.evaluate(JOB_ATTRIBUTE_PRIORITY, jobNode);
        if (!"".equals(prio)) {
            job.setPriority(JobPriority.findPriority(prio));
        } else {
            job.setPriority(JobPriority.NORMAL);
        }

        // JOB CANCEL ON EXCEPTION
        String cancel = xpath.evaluate(JOB_ATTRIBUTE_CANCELONERROR, jobNode);
        if (!"".equals(cancel)) {
            job.setCancelOnError(Boolean.parseBoolean(cancel));
        } else {
            job.setCancelOnError(false);
        }

        // JOB PROJECT NAME
        String projectName = (String) xpath.evaluate(JOB_ATTRIBUTE_PROJECTNAME, jobNode,
                XPathConstants.STRING);
        if (!"".equals(projectName)) {
            job.setProjectName(projectName);
        }
        System.out.println("Project name = " + projectName);

        // JOB LOG FILE
        String logFile = xpath.evaluate(JOB_ATTRIBUTE_LOGFILE, jobNode);
        if (!"".equals(logFile)) {
            job.setLogFile(logFile);
        }

        // JOB DESCRIPTION
        Object description = xpath.evaluate(addPrefixes(JOB_TAG + "/" + TAG_DESCRIPTION), rootNode,
                XPathConstants.STRING);

        if (description != null) {
            System.out.println("Job description = " + description);
            job.setDescription((String) description);
        }

        //JOB GENERIC INFORMATION
        NodeList list = (NodeList) xpath.evaluate(addPrefixes(GENERIC_INFORMATION), jobNode,
                XPathConstants.NODESET);
        if (list != null) {
            for (int i = 0; i < list.getLength(); i++) {
                Node n = list.item(i);
                String name = (String) xpath.evaluate(GENERIC_INFO_ATTRIBUTE_NAME, n, XPathConstants.STRING);
                String value = (String) xpath
                        .evaluate(GENERIC_INFO_ATTRIBUTE_VALUE, n, XPathConstants.STRING);

                System.out.println(name + "->" + value);

                if ((name != null) && (value != null)) {
                    job.addGenericInformation(name, value);
                }
            }
        }

        //JOB CLASSPATH
        String[] classpathEntries = null;
        NodeList classPathNodes = (NodeList) xpath.evaluate(addPrefixes(CP_TAG_CLASSPATHES), jobNode,
                XPathConstants.NODESET);
        if (classPathNodes != null) {
            classpathEntries = new String[classPathNodes.getLength()];
            for (int i = 0; i < classPathNodes.getLength(); i++) {
                Node n = classPathNodes.item(i);
                String path = (String) xpath.evaluate(CP_ATTRIBUTE_PATH, n, XPathConstants.STRING);
                classpathEntries[i] = path;
            }
        }

        // JOB EXECUTION ENVIRONMENT
        if (classpathEntries != null && classpathEntries.length != 0) {
            job.getEnv().setJobClasspath(classpathEntries);
            // TODO cdelbe : 1) use manifest version ?
            //               2) stream classpath instead of sending it as param ?
            job.getEnv().setJobClasspathContent(JarUtils.jarDirectories(classpathEntries, "1.0", null, null));
        }
        return createTasks(jobNode, job, xpath);
    }

    Job createTasks(Node jobNode, Job job, XPath xpath) throws XPathExpressionException,
            ClassNotFoundException, IOException, InvalidScriptException, UserException {
        Map<Task, ArrayList<String>> tasks = new HashMap<Task, ArrayList<String>>();

        // TASKS
        NodeList list = null;
        switch (job.getType()) {
            case TASKSFLOW:
                list = (NodeList) xpath.evaluate(addPrefixes(JOB_TASKFLOW + "/" + TASK_TAG), jobNode,
                        XPathConstants.NODESET);
                break;
            default:
                list = (NodeList) xpath.evaluate(addPrefixes(JOB_PROACTIVE + "/" + TASK_TAG), jobNode,
                        XPathConstants.NODESET);
        }
        if (list != null) {
            for (int i = 0; i < list.getLength(); i++) {
                Node taskNode = list.item(i);
                Task task = null;

                // TASK PROCESS
                Node process = (Node) xpath.evaluate(addPrefixes(JAVA_EXECUTABLE), taskNode,
                        XPathConstants.NODE);
                if (process != null) { // JAVA TASK
                    task = createJavaTask(process);
                } else if ((process = (Node) xpath.evaluate(addPrefixes(NATIVE_EXECUTABLE), taskNode,
                        XPathConstants.NODE)) != null) { // NATIVE TASK
                    task = createNativeTask(process);
                } else if ((process = (Node) xpath.evaluate(addPrefixes(PROACTIVE_EXECUTABLE), taskNode,
                        XPathConstants.NODE)) != null) { // APPLICATION TASK
                    task = createProActiveTask(process);
                } else {
                    throw new RuntimeException("Unknow process !!");
                }

                task = createTask(taskNode, task);

                //check if walltime and fork attribute are consistency.
                //if the walltime is set and fork not, fork must be true.
                if ((task instanceof JavaTask) && task.getWallTime() > 0 && !((JavaTask) task).isFork()) {
                    ((JavaTask) task).setFork(true);
                    System.out
                            .println("For javatask, setting a walltime implies the task to be forked, so your task will be forked anyway !");
                }
                switch (job.getType()) {
                    case PROACTIVE:
                        ((ProActiveJob) job).setTask((ProActiveTask) task);
                        break;
                    default:
                        ((TaskFlowJob) job).addTask(task);
                }

                // TASK DEPENDS
                NodeList refList = (NodeList) xpath.evaluate(addPrefixes(TASK_DEPENDENCES_REF), taskNode,
                        XPathConstants.NODESET);
                ArrayList<String> depList = new ArrayList<String>();
                if (refList != null) {
                    for (int j = 0; j < refList.getLength(); j++) {
                        Node ref = refList.item(j);
                        depList.add(ref.getNodeValue());
                    }
                }
                tasks.put(task, depList);
            }
        }

        // Dependencies
        HashMap<String, Task> depends = new HashMap<String, Task>();

        for (Task td : tasks.keySet())
            depends.put(td.getName(), td);
        if (job.getType() != JobType.PROACTIVE) {
            for (Entry<Task, ArrayList<String>> task : tasks.entrySet()) {
                // task.getKey().setJobId(job.getId());
                ArrayList<String> depListStr = task.getValue();
                for (int i = 0; i < depListStr.size(); i++) {
                    if (depends.containsKey(depListStr.get(i))) {
                        task.getKey().addDependence(depends.get(depListStr.get(i)));
                    } else {
                        System.err.println("Can't resolve dependence : " + depListStr.get(i));
                    }
                }
            }
        }

        return job;
    }

    @SuppressWarnings("unchecked")
    private Task createTask(Node taskNode, Task task) throws XPathExpressionException,
            ClassNotFoundException, InvalidScriptException, MalformedURLException {
        // TASK NAME
        task.setName((String) xpath.evaluate(ATTRIBUTE_ID, taskNode, XPathConstants.STRING));
        System.out.println("TaskId = " + task.getName());

        // TASK DESCRIPTION
        task.setDescription((String) xpath.evaluate(addPrefixes(TAG_DESCRIPTION), taskNode,
                XPathConstants.STRING));
        System.out.println("Description = " + task.getDescription());

        // TASK GENERIC INFORMATION
        NodeList list = (NodeList) xpath.evaluate(addPrefixes(GENERIC_INFORMATION), taskNode,
                XPathConstants.NODESET);
        if (list != null) {
            for (int i = 0; i < list.getLength(); i++) {
                Node n = list.item(i);
                String name = (String) xpath.evaluate(GENERIC_INFO_ATTRIBUTE_NAME, n, XPathConstants.STRING);
                String value = (String) xpath
                        .evaluate(GENERIC_INFO_ATTRIBUTE_VALUE, n, XPathConstants.STRING);
                System.out.println("Generic Info = " + name + ":" + value);
                if ((name != null) && (value != null)) {
                    task.addGenericInformation(name, value);
                }
            }
        }

        // TASK RESULT DESCRIPTION
        String previewClassName = (String) xpath.evaluate(TASK_ATTRIBUTE_RESULTPREVIEW, taskNode,
                XPathConstants.STRING);
        if (!previewClassName.equals("")) {
            System.out.println("Preview className = " + previewClassName);
            task.setResultPreview(previewClassName);
        }
        // TASK WALLTIME
        String wallTime = (String) xpath.evaluate(TASK_ATTRIBUTE_WALLTIME, taskNode, XPathConstants.STRING);
        if (wallTime != null && !wallTime.equals("")) {
            task.setWallTime(Tools.formatDate(wallTime));
            System.out.println("WallTime = " + wallTime + " ( " + Tools.formatDate(wallTime) + "ms )");
        }

        // TASK PRECIOUS RESULT
        task.setPreciousResult(((String) xpath.evaluate(TASK_ATTRIBUTE_PRECIOUSRESULT, taskNode,
                XPathConstants.STRING)).equals("true"));
        System.out.println("Precious = " + task.isPreciousResult());

        // TASK RETRIES
        String rerunnable = (String) xpath.evaluate(TASK_ATTRIBUTE_TASKRETRIES, taskNode,
                XPathConstants.STRING);
        if (rerunnable != "") {
            task.setRerunnable(Integer.parseInt(rerunnable));
        } else {
            task.setRerunnable(1);
        }
        System.out.println("reRun = " + task.getRerunnable());

        // TASK RESTART ON ERROR
        String restart = (String) xpath.evaluate(TASK_ATTRIBUTE_RESTARTONERROR, taskNode,
                XPathConstants.STRING);
        task.setRestartOnError(RestartMode.getMode(restart));
        System.out.println("restartOnError = " + task.getRestartOnError());

        // TASK VERIF
        Node verifNode = (Node) xpath.evaluate(addPrefixes(TASK_TAG_SELECTION + "/" + TASK_TAG_SCRIPT),
                taskNode, XPathConstants.NODE);
        if (verifNode != null) {
            task.setSelectionScript(createSelectionScript(verifNode));
        }

        // TASK PRE
        Node preNode = (Node) xpath.evaluate(addPrefixes(TASK_TAG_PRE + "/" + TASK_TAG_SCRIPT), taskNode,
                XPathConstants.NODE);
        if (preNode != null) {
            System.out.println("PRE");
            task.setPreScript(createScript(preNode));
        }

        // TASK POST
        Node postNode = (Node) xpath.evaluate(addPrefixes(TASK_TAG_POST + "/" + TASK_TAG_SCRIPT), taskNode,
                XPathConstants.NODE);
        if (postNode != null) {
            System.out.println("POST");
            task.setPostScript(createScript(postNode));
        }
        return task;
    }

    private Task createNativeTask(Node executable) throws XPathExpressionException, ClassNotFoundException,
            IOException, InvalidScriptException {
        Node scNode = (Node) xpath.evaluate(addPrefixes(SCRIPT_STATICCOMMAND), executable,
                XPathConstants.NODE);
        Node dcNode = (Node) xpath.evaluate(addPrefixes(SCRIPT_DYNAMICCOMMAND), executable,
                XPathConstants.NODE);
        NativeTask desc = new NativeTask();
        if (scNode != null) {
            // static command
            String cmd = (String) xpath.evaluate(SCRIPT_ATTRIBUTE_VALUE, scNode, XPathConstants.STRING);
            NodeList args = (NodeList) xpath.evaluate(addPrefixes(SCRIPT_TAG_ARGUMENTS), scNode,
                    XPathConstants.NODESET);
            if (args != null) {
                for (int i = 0; i < args.getLength(); i++) {
                    Node arg = args.item(i);
                    String value = (String) xpath
                            .evaluate(SCRIPT_ATTRIBUTE_VALUE, arg, XPathConstants.STRING);

                    if (value != null) {
                        cmd += (" " + value);
                    }
                }
            }
            desc.setCommandLine(cmd);
        } else {
            // dynamic command
            Node scriptNode = (Node) xpath.evaluate(
                    addPrefixes(SCRIPT_TAG_GENERATION + "/" + TASK_TAG_SCRIPT), dcNode, XPathConstants.NODE);
            Script<?> script = createScript(scriptNode);
            GenerationScript gscript = new GenerationScript(script);
            desc.setGenerationScript(gscript);
        }

        return desc;
    }

    @SuppressWarnings("unchecked")
    private JavaTask createJavaTask(Node process) throws XPathExpressionException, ClassNotFoundException,
            IOException {
        JavaTask desc = new JavaTask();
        desc.setExecutableClassName((String) xpath.compile(TASK_ATTRIBUTE_CLASSNAME).evaluate(process,
                XPathConstants.STRING));

        System.out.println("task = " + desc.getExecutableClassName());

        //FORKED JAVA TASK PARAMETERS
        boolean fork = "true".equals((String) xpath.evaluate(TASK_ATTRIBUTE_FORK, process,
                XPathConstants.STRING));
        desc.setFork(fork);
        System.out.println("Fork = " + fork);

        //javaEnvironment
        ForkEnvironment forkEnv = new ForkEnvironment();
        String javaHome = (String) xpath.evaluate(addPrefixes(FORK_TAG_ENVIRONMENT + "/" +
            FORK_ATTRIBUTE_JAVAHOME), process, XPathConstants.STRING);
        System.out.println(process.getLocalName());
        if (javaHome != null) {
            forkEnv.setJavaHome(javaHome);
            System.out.println("javaHome = " + javaHome);
        }

        String jvmParameters = (String) xpath.evaluate(addPrefixes(FORK_TAG_ENVIRONMENT + "/" +
            FORK_ATTRIBUTE_JVMPARAMETERS), process, XPathConstants.STRING);
        if (jvmParameters != null) {
            forkEnv.setJVMParameters(jvmParameters);
            System.out.println("jvmParameters = " + jvmParameters);
        }
        desc.setForkEnvironment(forkEnv);

        //EXECUTABLE PARAMETERS
        NodeList args = (NodeList) xpath.evaluate(addPrefixes(TASK_TAG_PARAMETERS), process,
                XPathConstants.NODESET);
        if (args != null) {
            for (int i = 0; i < args.getLength(); i++) {
                Node arg = args.item(i);
                String name = (String) xpath
                        .evaluate(GENERIC_INFO_ATTRIBUTE_NAME, arg, XPathConstants.STRING);
                String value = (String) xpath.evaluate(GENERIC_INFO_ATTRIBUTE_VALUE, arg,
                        XPathConstants.STRING);

                if ((name != null) && (value != null)) {
                    desc.getArguments().put(name, value);
                }
            }
        }

        for (Entry<String, String> entry : desc.getArguments().entrySet())
            System.out.println("arg: " + entry.getKey() + " = " + entry.getValue());

        return desc;
    }

    @SuppressWarnings("unchecked")
    private ProActiveTask createProActiveTask(Node process) throws XPathExpressionException,
            ClassNotFoundException, IOException {
        ProActiveTask desc = new ProActiveTask();

        desc.setExecutableClassName((String) xpath.compile(TASK_ATTRIBUTE_CLASSNAME).evaluate(process,
                XPathConstants.STRING));
        System.out.println("task = " + desc.getExecutableClassName());

        NodeList args = (NodeList) xpath.evaluate(addPrefixes(TASK_TAG_PARAMETERS), process,
                XPathConstants.NODESET);

        if (args != null) {
            for (int i = 0; i < args.getLength(); i++) {
                Node arg = args.item(i);
                String name = (String) xpath
                        .evaluate(GENERIC_INFO_ATTRIBUTE_NAME, arg, XPathConstants.STRING);
                String value = (String) xpath.evaluate(GENERIC_INFO_ATTRIBUTE_VALUE, arg,
                        XPathConstants.STRING);

                // TASK NEEDED_NODES
                int neededNodes = ((Double) xpath.evaluate(addPrefixes(JOB_TAG + "/" + JOB_PROACTIVE + "/" +
                    TASK_ATTRIBUTE_NEEDEDNODES), arg, XPathConstants.NUMBER)).intValue();
                desc.setNumberOfNodesNeeded(neededNodes);

                if ((name != null) && (value != null)) {
                    desc.getArguments().put(name, value);
                }
            }
        }

        for (Entry<String, String> entry : desc.getArguments().entrySet())
            System.out.println("arg: " + entry.getKey() + " = " + entry.getValue());

        return desc;
    }

    private String[] getArguments(Node node) throws XPathExpressionException {
        String[] parameters = null;
        NodeList args = (NodeList) xpath.evaluate(addPrefixes(SCRIPT_TAG_ARGUMENTS), node,
                XPathConstants.NODESET);

        if (args != null) {
            parameters = new String[args.getLength()];

            for (int i = 0; i < args.getLength(); i++) {
                Node arg = args.item(i);
                String value = (String) xpath.evaluate(GENERIC_INFO_ATTRIBUTE_VALUE, arg,
                        XPathConstants.STRING);
                parameters[i] = value;
            }
        }

        return parameters;
    }

    private Script<?> createScript(Node node) throws XPathExpressionException, InvalidScriptException,
            MalformedURLException {
        // JOB TYPE
        Node fileNode = (Node) xpath.evaluate(addPrefixes(SCRIPT_TAG_FILE), node, XPathConstants.NODE);
        Node codeNode = (Node) xpath.evaluate(addPrefixes(SCRIPT_TAG_CODE), node, XPathConstants.NODE);

        if (fileNode != null) {
            // file
            String url = (String) xpath.evaluate(SCRIPT_ATTRIBUTE_URL, fileNode, XPathConstants.STRING);

            if ((url != null) && (!url.equals(""))) {
                System.out.println(url);

                return new SimpleScript(new URL(url), getArguments(fileNode));
            }

            String path = (String) xpath.evaluate(SCRIPT_ATTRIBUTE_PATH, fileNode, XPathConstants.STRING);

            if ((path != null) && (!path.equals(""))) {
                System.out.println(path);

                return new SimpleScript(new File(path), getArguments(fileNode));
            }
        } else {
            // code
            String engine = (String) xpath.evaluate(SCRIPT_ATTRIBUTE_LANGUAGE, codeNode,
                    XPathConstants.STRING);

            if (((engine != null) && (!engine.equals(""))) && (node.getTextContent() != null)) {
                String script = node.getTextContent();

                try {
                    return new SimpleScript(script, engine);
                } catch (InvalidScriptException e) {
                    e.printStackTrace();
                }
            }
        }

        // schema should check this...?
        throw new InvalidScriptException("The script is not recognized");
    }

    private SelectionScript createSelectionScript(Node node) throws XPathExpressionException,
            InvalidScriptException, MalformedURLException {
        Script<?> script = createScript(node);
        //is the script static or dynamic
        boolean isStatic = "static".equals((String) xpath.evaluate(SCRIPT_ATTRIBUTE_TYPE, node,
                XPathConstants.STRING));
        System.out.println("selection script dynamic = " + !isStatic);
        return new SelectionScript(script, !isStatic);
    }

    private static String addPrefixes(String unprefixedPath) {
        if ((JOB_PREFIX != null) || (JOB_PREFIX.length() > 0)) {
            String pr = JOB_PREFIX + ":";
            unprefixedPath = " " + unprefixedPath + " ";

            StringTokenizer st = new StringTokenizer(unprefixedPath, "/");
            StringBuilder sb = new StringBuilder();
            boolean slash_start = false;
            boolean in_the_middle = false;

            while (st.hasMoreElements()) {
                String token = st.nextToken().trim();

                if (token.length() > 0) {
                    if (in_the_middle || slash_start) {
                        if (!token.startsWith("@")) {
                            sb.append("/" + pr + token);
                        } else {
                            sb.append("/" + token);
                        }
                    } else {
                        if (!token.startsWith("@")) {
                            sb.append(pr + token);
                        } else {
                            sb.append(token);
                        }

                        in_the_middle = true;
                    }
                } else {
                    slash_start = true;
                }
            }

            return sb.toString();
        }

        return unprefixedPath;
    }

    private class ValidatingErrorHandler implements ErrorHandler {
        private int mistakes = 0;
        private StringBuilder mistakesStack = null;

        public ValidatingErrorHandler() {
            mistakesStack = new StringBuilder();
        }

        public void error(SAXParseException exception) throws SAXException {
            appendAndPrintMessage("ERROR:" + exception.getMessage() + " at line " +
                exception.getLineNumber() + ", column " + exception.getColumnNumber());
        }

        public void fatalError(SAXParseException exception) throws SAXException {
            appendAndPrintMessage("ERROR:" + exception.getMessage() + " at line " +
                exception.getLineNumber() + ", column " + exception.getColumnNumber());
        }

        public void warning(SAXParseException exception) throws SAXException {
            appendAndPrintMessage("WARNING:" + exception.getMessage() + " at line " +
                exception.getLineNumber() + ", column " + exception.getColumnNumber());
        }

        private void appendAndPrintMessage(String msg) {
            mistakesStack.append(msg + "\n");
            System.err.println(msg);
            mistakes++;
        }
    }

    private class SchedulerNamespaceContext implements NamespaceContext {
        public String getNamespaceURI(String prefix) {
            if ((prefix == null) || (prefix.length() == 0)) {
                throw new NullPointerException("Null prefix");
            } else if (prefix.equals(JOB_PREFIX)) {
                return JOB_NAMESPACE;
            } else if ("xml".equals(prefix)) {
                return XMLConstants.XML_NS_URI;
            }

            return XMLConstants.DEFAULT_NS_PREFIX;
        }

        // This method isn't necessary for XPath processing.
        public String getPrefix(String uri) {
            throw new UnsupportedOperationException();
        }

        // This method isn't necessary for XPath processing either.
        public Iterator<?> getPrefixes(String uri) {
            throw new UnsupportedOperationException();
        }
    }
}