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
 * $$ACTIVEEON_INITIAL_DEV$$
 */
package org.ow2.proactive.scheduler.core.jmx.mbean;

import javax.management.NotCompliantMBeanException;
import javax.management.StandardMBean;

import org.ow2.proactive.scheduler.core.account.SchedulerAccountsManager;


/**
 * Implementation of the ManagementMBean interface.
 *
 * @author The ProActive Team
 * @since ProActive Scheduling 2.1
 */
public final class ManagementMBeanImpl extends StandardMBean implements ManagementMBean {

    private final SchedulerAccountsManager accountsManager;

    public ManagementMBeanImpl(final SchedulerAccountsManager accountsManager)
            throws NotCompliantMBeanException {
        super(ManagementMBean.class);
        this.accountsManager = accountsManager;
    }

    public int getAccountingRefreshRateInSeconds() {
        return this.accountsManager.getRefreshDelayInSeconds();
    }

    public void setAccountingRefreshRateInSeconds(int refreshDelayInSeconds) {
        this.accountsManager.setRefreshRateInSeconds(refreshDelayInSeconds);
    }

    public void setDefaultAccountingRefreshRateInSeconds() {
        final int defaultValue = this.accountsManager.getDefaultRefreshDelayInSeconds();
        this.accountsManager.setRefreshRateInSeconds(defaultValue);
    }

    public void refreshAllAccounts() {
        this.accountsManager.refreshAllAccounts();
    }

    public long getLastRefreshDurationInMilliseconds() {
        return this.accountsManager.getLastRefreshDurationInMilliseconds();
    }
}
