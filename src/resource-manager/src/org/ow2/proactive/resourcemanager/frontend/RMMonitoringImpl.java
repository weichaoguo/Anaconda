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
package org.ow2.proactive.resourcemanager.frontend;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.apache.log4j.Logger;
import org.objectweb.proactive.Body;
import org.objectweb.proactive.InitActive;
import org.objectweb.proactive.api.PAActiveObject;
import org.objectweb.proactive.core.UniqueID;
import org.objectweb.proactive.core.util.log.ProActiveLogger;
import org.ow2.proactive.resourcemanager.common.RMConstants;
import org.ow2.proactive.resourcemanager.common.event.RMEvent;
import org.ow2.proactive.resourcemanager.common.event.RMEventType;
import org.ow2.proactive.resourcemanager.common.event.RMInitialState;
import org.ow2.proactive.resourcemanager.common.event.RMNodeEvent;
import org.ow2.proactive.resourcemanager.common.event.RMNodeSourceEvent;
import org.ow2.proactive.resourcemanager.core.RMCore;
import org.ow2.proactive.resourcemanager.core.RMCoreInterface;
import org.ow2.proactive.resourcemanager.core.jmx.mbean.RMWrapper;
import org.ow2.proactive.resourcemanager.exception.RMException;
import org.ow2.proactive.resourcemanager.utils.RMLoggers;


/**
 * Active object designed for the Monitoring of the Resource Manager.
 * This class provides a way for a monitor to ask at
 * Resource Manager to throw events
 * generated by nodes and nodes sources management. RMMonitoring dispatch
 * events thrown by {@link RMCore} to all its monitors.
 *
 *        //TODO methods to add and remove because RM GUI is in development,
 *        so Java Doc not yet up to date for this component
 *
 * @see org.ow2.proactive.resourcemanager.frontend.RMEventListener
 *
 * @author The ProActive Team
 * @since ProActive Scheduling 0.9
 */
public class RMMonitoringImpl implements RMMonitoring, RMEventListener, InitActive {
    private static final Logger logger = ProActiveLogger.getLogger(RMLoggers.MONITORING);

    // Attributes
    private RMCoreInterface rmcore;
    private HashMap<UniqueID, RMEventListener> RMListeners;
    private String MonitoringUrl = null;

    /** Scheduler's MBean Server */
    private MBeanServer mbs = null;

    /** Resource Manager's MBean */
    private RMWrapper rMBean = null;

    // ----------------------------------------------------------------------//
    // CONSTRUTORS

    /** ProActive empty constructor */
    public RMMonitoringImpl() {
    }

    /**
     * Creates the RMMonitoring active object.
     * @param rmcore Stub of the RMCore active object.
     */
    public RMMonitoringImpl(RMCoreInterface rmcore) {
        RMListeners = new HashMap<UniqueID, RMEventListener>();
        this.rmcore = rmcore;
        // Register the Resource Manager MBean
        this.registerMBean();
    }

    /**
     * @see org.objectweb.proactive.InitActive#initActivity(org.objectweb.proactive.Body)
     */
    public void initActivity(Body body) {
        try {
            PAActiveObject.registerByName(PAActiveObject.getStubOnThis(),
                    RMConstants.NAME_ACTIVE_OBJECT_RMMONITORING);
        } catch (IOException e) {
            logger.debug("", e);
        }
    }

    /** Register a new Resource manager listener.
     * Way to a monitor object to ask at RMMonitoring to throw
     * RM events to it.
     * @param listener a listener object which implements {@link RMEventListener}
     * interface.
     * @param events list of wanted events that must be received.
     * @return RMInitialState snapshot of RM's current state : nodes and node sources.
     *  */
    public RMInitialState addRMEventListener(RMEventListener listener, RMEventType... events) {
        UniqueID id = PAActiveObject.getContext().getCurrentRequest().getSourceBodyID();

        this.RMListeners.put(id, listener);
        return rmcore.getRMInitialState();
    }

    /**
     * Removes a listener from RMMonitoring. Only listener itself must call this method
     */
    public void removeRMEventListener() throws RMException {
        UniqueID id = PAActiveObject.getContext().getCurrentRequest().getSourceBodyID();
        if (RMListeners.containsKey(id)) {
            RMListeners.remove(id);
        } else {
            throw new RMException("Listener is unknown");
        }
    }

    /**
     * Register the Resource Manager MBean
     */
    private void registerMBean() {
        //Get the platform MBeanServer
        mbs = ManagementFactory.getPlatformMBeanServer();
        // Unique identification of Scheduler MBean
        rMBean = new RMWrapper();
        ObjectName rMName = null;
        try {
            // Uniquely identify the MBeans and register them with the platform MBeanServer 
            rMName = new ObjectName("RMFrontend:name=RMBean");
            mbs.registerMBean(rMBean, rMName);
        } catch (Exception e) {
            logger.debug("", e);
        }
    }

    /**
     * Dispatch events thrown by the RMCore to all known monitors of the RM.
     * @param methodName method name corresponding to the event.
     * @param types Object types associated with the method call.
     * @param params Object associated with the method call.
     */
    private void dispatch(RMEventType methodName, Class<?>[] types, Object... params) {
        try {
            Method method = RMEventListener.class.getMethod(methodName.toString(), types);

            Iterator<UniqueID> iter = this.RMListeners.keySet().iterator();
            while (iter.hasNext()) {
                UniqueID id = iter.next();
                try {
                    method.invoke(RMListeners.get(id), params);
                } catch (Exception e) {
                    iter.remove();
                    logger.error("RM has detected that a listener is not connected anymore !");
                }
            }
        } catch (SecurityException e) {
            logger.debug("", e);
        } catch (NoSuchMethodException e) {
            logger.debug("", e);
        }
    }

    /**
     * @see org.ow2.proactive.resourcemanager.frontend.RMMonitoring#isAlive()
     */
    public boolean isAlive() {
        return true;
    }

    /** inherited from RMEventListener methods
     */

    /** Dispatch the shutdown event to all listeners.
     * @see org.ow2.proactive.resourcemanager.frontend.RMEventListener#rmShutDownEvent(org.ow2.proactive.resourcemanager.common.event.RMEvent)
     */
    public void rmShutDownEvent(RMEvent evt) {
        rMBean.rmShutDownEvent(evt);
        evt.setRMUrl(this.MonitoringUrl);
        dispatch(RMEventType.SHUTDOWN, new Class<?>[] { RMEvent.class }, evt);
    }

    /** Dispatch the shutting down event to all listeners.
     * @see org.ow2.proactive.resourcemanager.frontend.RMEventListener#rmShuttingDownEvent(org.ow2.proactive.resourcemanager.common.event.RMEvent)
     */
    public void rmShuttingDownEvent(RMEvent evt) {
        rMBean.rmShuttingDownEvent(evt);
        evt.setRMUrl(this.MonitoringUrl);
        dispatch(RMEventType.SHUTTING_DOWN, new Class<?>[] { RMEvent.class }, evt);
    }

    /** Dispatch the RM started event to all listeners.
     * @see org.ow2.proactive.resourcemanager.frontend.RMEventListener#rmStartedEvent(org.ow2.proactive.resourcemanager.common.event.RMEvent)
     */
    public void rmStartedEvent(RMEvent evt) {
        rMBean.rmStartedEvent(evt);
        evt.setRMUrl(this.MonitoringUrl);
        dispatch(RMEventType.STARTED, new Class<?>[] { RMEvent.class }, evt);
    }

    /** Dispatch the node added event to all listeners.
     * @see org.ow2.proactive.resourcemanager.frontend.RMEventListener#nodeSourceAddedEvent(org.ow2.proactive.resourcemanager.common.event.RMNodeSourceEvent)
     */
    public void nodeSourceAddedEvent(RMNodeSourceEvent ns) {
        ns.setRMUrl(this.MonitoringUrl);
        dispatch(RMEventType.NODESOURCE_CREATED, new Class<?>[] { RMNodeSourceEvent.class }, ns);
    }

    /** Dispatch the node removed event to all listeners.
     * @see org.ow2.proactive.resourcemanager.frontend.RMEventListener#nodeSourceRemovedEvent(org.ow2.proactive.resourcemanager.common.event.RMNodeSourceEvent)
     */
    public void nodeSourceRemovedEvent(RMNodeSourceEvent ns) {
        ns.setRMUrl(this.MonitoringUrl);
        dispatch(RMEventType.NODESOURCE_REMOVED, new Class<?>[] { RMNodeSourceEvent.class }, ns);
    }

    /** Dispatch the node removed event to all listeners.
     * @see org.ow2.proactive.resourcemanager.frontend.RMEventListener#nodeSourceRemovedEvent(org.ow2.proactive.resourcemanager.common.event.RMNodeSourceEvent)
     */
    public void nodeSourceNodesAcquisitionInfoAddedEvent(RMNodeSourceEvent ns) {
        ns.setRMUrl(this.MonitoringUrl);
        dispatch(RMEventType.NODESOURCE_NODES_ACQUISTION_INFO_ADDED,
                new Class<?>[] { RMNodeSourceEvent.class }, ns);
    }

    /** Dispatch the node added event to all listeners.
     * @see org.ow2.proactive.resourcemanager.frontend.RMEventListener#nodeAddedEvent(org.ow2.proactive.resourcemanager.common.event.RMNodeEvent)
     */
    public void nodeAddedEvent(RMNodeEvent n) {
        rMBean.nodeAddedEvent(n);
        n.setRMUrl(this.MonitoringUrl);
        dispatch(RMEventType.NODE_ADDED, new Class<?>[] { RMNodeEvent.class }, n);
    }

    /** Dispatch the node freed event to all listeners.
     * @see org.ow2.proactive.resourcemanager.frontend.RMEventListener#nodeFreeEvent(org.ow2.proactive.resourcemanager.common.event.RMNodeEvent)
     */
    public void nodeFreeEvent(RMNodeEvent n) {
        rMBean.nodeFreeEvent(n);
        n.setRMUrl(this.MonitoringUrl);
        dispatch(RMEventType.NODE_FREE, new Class<?>[] { RMNodeEvent.class }, n);
    }

    /** Dispatch the node busy event to all listeners.
     * @see org.ow2.proactive.resourcemanager.frontend.RMEventListener#nodeBusyEvent(org.ow2.proactive.resourcemanager.common.event.RMNodeEvent)
     */
    public void nodeBusyEvent(RMNodeEvent n) {
        rMBean.nodeBusyEvent(n);
        n.setRMUrl(this.MonitoringUrl);
        dispatch(RMEventType.NODE_BUSY, new Class<?>[] { RMNodeEvent.class }, n);
    }

    /** Dispatch the node to release event to all listeners.
     * @see org.ow2.proactive.resourcemanager.frontend.RMEventListener#nodeToReleaseEvent(org.ow2.proactive.resourcemanager.common.event.RMNodeEvent)
     */
    public void nodeToReleaseEvent(RMNodeEvent n) {
        n.setRMUrl(this.MonitoringUrl);
        dispatch(RMEventType.NODE_TO_RELEASE, new Class<?>[] { RMNodeEvent.class }, n);
    }

    /** Dispatch the node down event to all listeners.
     * @see org.ow2.proactive.resourcemanager.frontend.RMEventListener#nodeDownEvent(org.ow2.proactive.resourcemanager.common.event.RMNodeEvent)
     */
    public void nodeDownEvent(RMNodeEvent n) {
        rMBean.nodeBusyEvent(n);
        n.setRMUrl(this.MonitoringUrl);
        dispatch(RMEventType.NODE_DOWN, new Class<?>[] { RMNodeEvent.class }, n);
    }

    /** Dispatch the node removed event to all listeners
     * @see org.ow2.proactive.resourcemanager.frontend.RMEventListener#nodeRemovedEvent(org.ow2.proactive.resourcemanager.common.event.RMNodeEvent)
     */
    public void nodeRemovedEvent(RMNodeEvent n) {
        rMBean.nodeRemovedEvent(n);
        n.setRMUrl(this.MonitoringUrl);
        dispatch(RMEventType.NODE_REMOVED, new Class<?>[] { RMNodeEvent.class }, n);
    }

    /** Stop and remove monitoring active object
     */
    public void shutdown() {
        //throwing shutdown event
        rmShutDownEvent(new RMEvent());
        PAActiveObject.terminateActiveObject(false);
    }

    public Logger getLogger() {
        return logger;
    }
}
