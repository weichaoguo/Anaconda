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
package org.ow2.proactive.resourcemanager.core.jmx.mbean;

import javax.management.NotCompliantMBeanException;

import org.ow2.proactive.resourcemanager.core.account.RMAccount;
import org.ow2.proactive.resourcemanager.core.account.RMAccountsManager;


/**
 * Implementation of the AllAccountsMBean interface.
 *
 * @author The ProActive Team
 * @since ProActive Scheduling 2.1
 */
public final class AllAccountsMBeanImpl extends MyAccountMBeanImpl implements AllAccountsMBean {

    private String targetUsername;

    public AllAccountsMBeanImpl(final RMAccountsManager accountManager) throws NotCompliantMBeanException {
        super(AllAccountsMBean.class, accountManager);
    }

    public void setUsername(final String username) {
        this.targetUsername = username;
    }

    public String getUsername() {
        return this.targetUsername;
    }

    @Override
    protected RMAccount internalGetAccount() {
        return super.accountsManager.getAccount(this.targetUsername); // can be null
    }

    //    public String[] getJobDurationRanks() {
    //        final Map<String, RMAccount> map = super.accountsManager.getAllAccounts();
    //        final Comparator<String> comparator = new Comparator<String>() {
    //            public final int compare(final String username1, final String username2) {
    //                final RMAccount a1 = map.get(username1);
    //                final RMAccount a2 = map.get(username2);
    //                return a1.getJobDurationRank() - a2.getJobDurationRank();
    //            }
    //        };
    //        final String[] res = new String[map.size()];
    //        Arrays.sort(map.keySet().toArray(res), comparator);
    //        return res;
    //    }
    //
    //    public String[] getTaskDurationRanks() {
    //        final Map<String, RMAccount> map = super.accountsManager.getAllAccounts();
    //        final Comparator<String> comparator = new Comparator<String>() {
    //            public final int compare(final String username1, final String username2) {
    //                final RMAccount a1 = map.get(username1);
    //                final RMAccount a2 = map.get(username2);
    //                return a1.getTaskDurationRank() - a2.getTaskDurationRank();
    //            }
    //        };
    //        final String[] res = new String[map.size()];
    //        Arrays.sort(map.keySet().toArray(res), comparator);
    //        return res;
    //    }
}