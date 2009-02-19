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
package org.ow2.proactive.scheduler.common.task;

import java.io.Serializable;
import java.util.Map;
import java.util.Vector;

import org.objectweb.proactive.annotation.PublicAPI;
import org.ow2.proactive.scheduler.common.job.JobId;


/**
 * This class represents a task for the policy.
 *
 * @author The ProActive Team
 * @since ProActive Scheduling 0.9
 */
@PublicAPI
public interface TaskDescriptor extends Serializable {

    /**
     * To get the children
     *
     * @return the children
     */
    public Vector<TaskDescriptor> getChildren();

    /**
     * To get the id
     *
     * @return the id
     */
    public TaskId getId();

    /**
     * To get the parents
     *
     * @return the parents
     */
    public Vector<TaskDescriptor> getParents();

    /**
     * To get the jobId
     *
     * @return the jobId
     */
    public JobId getJobId();

    /**
     * Return the number of children remaining.
     *
     * @return the number of children remaining.
     */
    public int getChildrenCount();

    /**
     * Returns the number Of nodes used by this task.
     *
     * @return the number Of nodes used by this task.
     */
    public int getNumberOfUsedNodes();

    /**
     * Return the generic informations has a Map.
     *
     * @return the generic informations has a Map.
     */
    public Map<String, String> getGenericInformations();

}