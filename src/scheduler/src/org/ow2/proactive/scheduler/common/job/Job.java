/*
 * ################################################################
 *
 * ProActive: The Java(TM) library for Parallel, Distributed,
 *            Concurrent computing with Security and Mobility
 *
 * Copyright (C) 1997-2008 INRIA/University of Nice-Sophia Antipolis
 * Contact: proactive@ow2.org
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
 * $$PROACTIVE_INITIAL_DEV$$
 */
package org.ow2.proactive.scheduler.common.job;

import java.util.HashMap;

import org.objectweb.proactive.annotation.PublicAPI;
import org.ow2.proactive.scheduler.common.scheduler.UserSchedulerInterface;
import org.ow2.proactive.scheduler.common.task.CommonAttribute;


/**
 * Definition of a job for the user.
 * You can create a job by using this class. Job will be used to set some properties,
 * and give it the different tasks to run.
 * <p>
 * Here's a definition of the different parts of a job :<br>
 * {@link #setName(String)} will be used to identified the job.<br>
 * {@link #setDescription(String)} to set a short description of your job.<br>
 * {@link #setPriority(JobPriority)} to set the priority for the job, see {@link JobPriority} for more details.<br>
 * {@link #setCancelOnError(boolean)} used if you want to abort the job if an exception occurred in at least one of the task.<br>
 * {@link #setLogFile(String)} allow you to save the output of the job tasks in a specific file.<br>
 * <p>
 * Once the job created, you can submit it to the scheduler using the {@link UserSchedulerInterface}.
 *
 * @author The ProActive Team
 * @since ProActive Scheduling 0.9
 */
@PublicAPI
public abstract class Job extends CommonAttribute implements GenericInformationsProvider {

    // TODO cdelbe, jlscheef
    // all setters are needed only for InternalJob creation. (JL) (also for (user API) job creation (?))
    // Is there a more elegant way...? (JL) Constructors ?

    /** Name of the job */
    protected String name = JobId.DEFAULT_JOB_NAME;

    /** Execution environment for this job */
    protected JobEnvironment env = new JobEnvironment();

    /** logs are written in logFile if not null */
    protected String logFile = null;

    /** Short description of this job */
    protected String description = "No description";

    /** Project name for this job */
    protected String projectName = "Not Assigned";

    /** Job priority */
    protected JobPriority priority = JobPriority.NORMAL;

    /** Job user informations */
    protected HashMap<String, String> genericInformations = new HashMap<String, String>();

    /** ProActive Empty Constructor */
    public Job() {

    }

    /**
     * To get the type
     *
     * @return the type
     */
    public abstract JobType getType();

    /**
     * To get the id
     *
     * @return the id
     */
    public abstract JobId getId();

    /**
     * To get the description
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * To set a short description for the job.
     *
     * @param description the description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * To get the name of the job.
     *
     * @return the name of the job.
     */
    public String getName() {
        return name;
    }

    /**
     * To set the name of the job.
     *
     * @param name the name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * To get the priority of the job.
     *
     * @return the priority of the job.
     */
    public JobPriority getPriority() {
        return priority;
    }

    /**
     * To set the priority of the job.
     *
     * @param priority the priority to set.
     */
    public void setPriority(JobPriority priority) {
        this.priority = priority;
    }

    /**
     * set a log file for this job.
     * 
     * @param filePath the path of the log file.
     */
    public void setLogFile(String filePath) {
        this.logFile = filePath;
    }

    /**
     * Return the path to the log file, or null if not logged.
     * 
     * @return the path to the log file, or null if not logged.
     */
    public String getLogFile() {
        return this.logFile;
    }

    /**
     * @see org.ow2.proactive.scheduler.common.job.GenericInformationsProvider#getGenericInformations()
     */
    public HashMap<String, String> getGenericInformations() {
        return genericInformations;
    }

    /**
     * @see org.ow2.proactive.scheduler.common.job.GenericInformationsProvider#addGenericInformation(java.lang.String, java.lang.String)
     */
    public void addGenericInformation(String key, String genericInformation) {
        this.genericInformations.put(key, genericInformation);
    }

    /**
     * Returns the project Name.
     * 
     * @return the project Name.
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Sets the project Name to the given projectName value.
     *
     * @param projectName the project Name to set.
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * Return the environment for this job
     * @see JobEnvironment
     * @return the environment for this job
     */
    public JobEnvironment getEnv() {
        return env;
    }

    /**
     * Set the environment for this job.
     * @param env the environment to set
     */
    public void setEnv(JobEnvironment env) {
        this.env = env;
    }
}
