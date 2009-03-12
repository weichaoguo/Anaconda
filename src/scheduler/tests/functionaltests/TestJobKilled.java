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
 *  Contributor(s): ActiveEon Team - http://www.activeeon.com
 *
 * ################################################################
 * $$ACTIVEEON_CONTRIBUTOR$$
 */
package functionaltests;

import java.util.Map;

import org.junit.Assert;
import org.objectweb.proactive.core.ProActiveTimeoutException;
import org.ow2.proactive.scheduler.common.job.JobId;
import org.ow2.proactive.scheduler.common.job.JobResult;
import org.ow2.proactive.scheduler.common.task.TaskResult;

import functionalTests.FunctionalTest;


/**
 * This class tests the way a task can kill a node.
 * It will start 3 dependent tasks where one will kill the node.
 * The task will be restarted as defined in the Scheduler ini file.
 * The goal is to check that the killing task is restarted the expected number of time, and ensure the task and job will terminate.
 * At the end, the first task must be terminated, the second one failed, and the last one not started.
 * It will also check that it's not relative to the 'cancelJobOnError' value.
 *
 * @author The ProActive Team
 * @date 2 jun 08
 * @since ProActive 4.0
 */
public class TestJobKilled extends FunctionalTest {

    private static String jobDescriptor = TestJobKilled.class.getResource(
            "/functionaltests/descriptors/Job_Killed.xml").getPath();

    /**
     * Tests start here.
     *
     * @throws Throwable any exception that can be thrown during the test.
     */
    @org.junit.Test
    public void run() throws Throwable {

        String task1Name = "task1";
        String task2Name = "task2";
        String task3Name = "task3";
        //cannot use SchedulerTHelper.testJobsubmission because
        //task 3 is never executed so no event can received
        //regarding this task.
        JobId id = SchedulerTHelper.submitJob(jobDescriptor);
        //check events reception
        SchedulerTHelper.log("Job submitted, id " + id.toString());
        SchedulerTHelper.log("Waiting for jobSubmitted");
        SchedulerTHelper.waitForEventJobSubmitted(id);
        SchedulerTHelper.log("Waiting for job running");
        SchedulerTHelper.waitForEventJobRunning(id);
        SchedulerTHelper.log("Waiting for task running : " + task1Name);
        SchedulerTHelper.waitForEventTaskRunning(id, task1Name);
        SchedulerTHelper.log("Waiting for task finished : " + task1Name);
        SchedulerTHelper.waitForEventTaskFinished(id, task1Name);

        SchedulerTHelper.log("Waiting for task running : " + task2Name);
        SchedulerTHelper.waitForEventTaskRunning(id, task2Name);
        SchedulerTHelper.log("Waiting for task finished : " + task2Name);
        SchedulerTHelper.waitForEventTaskFinished(id, task2Name);

        SchedulerTHelper.log("Waiting for job finished");
        SchedulerTHelper.waitForEventJobFinished(id);

        //task 3 should not be started
        boolean task3tarted = false;

        try {
            SchedulerTHelper.waitForEventTaskRunning(id, task3Name, 1000);
            task3tarted = true;
        } catch (ProActiveTimeoutException e) {
            // TODO Auto-generated catch block
        }

        Assert.assertFalse(task3tarted);

        JobResult res = SchedulerTHelper.getJobResult(id);
        Map<String, TaskResult> results = res.getAllResults();
        //check that all tasks results are defined
        Assert.assertNotNull(results.get("task1").value());
        Assert.assertNotNull(results.get("task2").getException());
    }
}