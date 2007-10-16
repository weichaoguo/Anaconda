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
package org.objectweb.proactive.examples.jmx.remote.management.jmx.notifications;

import javax.management.ObjectName;

import org.objectweb.proactive.examples.jmx.remote.management.mbean.BundleInfo;


public class BundleAddedNotification extends BundleNotification {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private BundleInfo bundleInfo;
    private String url;
    private ObjectName on;

    public BundleAddedNotification(String type, Object source,
        long sequenceNumber, String url, String message, ObjectName on) {
        super(type, source, sequenceNumber);
        this.url = url;
        this.bundleInfo = (BundleInfo) source;
        this.on = on;
    }

    /**
     * @return the bundleInfo
     */
    public BundleInfo getBundleInfo() {
        return bundleInfo;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    @Override
    public int getEventType() {
        return BUNDLE_ADDED;
    }

    @Override
    public ObjectName getObjectName() {
        return this.on;
    }
}
