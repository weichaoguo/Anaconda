/*
 * ################################################################
 *
 * ProActive Parallel Suite(TM): The Java(TM) library for
 *    Parallel, Distributed, Multi-Core Computing for
 *    Enterprise Grids & Clouds
 *
 * Copyright (C) 1997-2010 INRIA/University of 
 * 				Nice-Sophia Antipolis/ActiveEon
 * Contact: proactive@ow2.org or contact@activeeon.com
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; version 3 of
 * the License.
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
 * If needed, contact us to obtain a release under GPL Version 2 
 * or a different license than the GPL.
 *
 *  Initial developer(s):               The ProActive Team
 *                        http://proactive.inria.fr/team_members.htm
 *  Contributor(s):
 *
 * ################################################################
 * $$PROACTIVE_INITIAL_DEV$$
 */
package functionaltests.masterworker.userexception;

import functionaltests.*;
import functionalTests.FunctionalTest;
import functionaltests.masterworker.A;
import static junit.framework.Assert.assertTrue;

import org.objectweb.proactive.core.config.PAProperties;
import org.objectweb.proactive.extensions.masterworker.ProActiveMaster;
import org.objectweb.proactive.extensions.masterworker.TaskException;
import org.objectweb.proactive.extensions.masterworker.interfaces.Master;

import java.util.ArrayList;
import java.util.List;


/**
 * Test load balancing
 */
public class TestUserEx extends FunctionalTest {

    private Master<A, Integer> master;
    private List<A> tasks;
    public static final int NB_TASKS = 4;

    @org.junit.Test
    public void run() throws Throwable {

        SchedulerTHelper.startScheduler();

        //before
        tasks = new ArrayList<A>();
        for (int i = 0; i < NB_TASKS; i++) {
            // tasks that throw an exception
            A t = new A(i, 0, true);
            tasks.add(t);
        }

        master = new ProActiveMaster<A, Integer>();
        String[] classpath = new String[] {
                System.getProperty("pa.scheduler.home") + "/classes/schedulerTests",
                System.getProperty("pa.scheduler.home") + "/classes/scheduler" };
        master.addResources("rmi://localhost:" + PAProperties.PA_RMI_PORT.getValue() + "/", "demo", "demo",
                classpath);

        //middle
        boolean catched = false;

        master.solve(tasks);
        try {
            List<Integer> ids = master.waitAllResults();
            // we don't care of the results
            ids.clear();
        } catch (TaskException e) {
            assertTrue("Expected exception is the cause",
                    e.getCause().getCause() instanceof ArithmeticException);
            System.out.println("Expected exception catched, here it is : ");
            e.printStackTrace();
            catched = true;
        }
        assertTrue("Exception caught as excepted", catched);

        //after
        master.terminate(true);
    }

}
