/*
 * ################################################################
 *
 * ProActive: The Java(TM) library for Parallel, Distributed,
 *            Concurrent computing with Security and Mobility
 *
 * Copyright (C) 1997-2005 INRIA/University of Nice-Sophia Antipolis
 * Contact: proactive@objectweb.org
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA
 *
 *  Initial developer(s):               The ProActive Team
 *                        http://www.inria.fr/oasis/ProActive/contacts.html
 *  Contributor(s):
 *
 * ################################################################
 */
package org.objectweb.proactive.ic2d.monitoring.figures.listeners;

import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.jface.action.IAction;
import org.objectweb.proactive.ic2d.monitoring.actions.HorizontalLayoutAction;
import org.objectweb.proactive.ic2d.monitoring.actions.KillVMAction;
import org.objectweb.proactive.ic2d.monitoring.actions.NewHostAction;
import org.objectweb.proactive.ic2d.monitoring.actions.RefreshAction;
import org.objectweb.proactive.ic2d.monitoring.actions.RefreshHostAction;
import org.objectweb.proactive.ic2d.monitoring.actions.RefreshJVMAction;
import org.objectweb.proactive.ic2d.monitoring.actions.RefreshNodeAction;
import org.objectweb.proactive.ic2d.monitoring.actions.SetDepthAction;
import org.objectweb.proactive.ic2d.monitoring.actions.SetTTRAction;
import org.objectweb.proactive.ic2d.monitoring.actions.SetUpdateFrequenceAction;
import org.objectweb.proactive.ic2d.monitoring.actions.StopMonitoringAction;
import org.objectweb.proactive.ic2d.monitoring.actions.VerticalLayoutAction;
import org.objectweb.proactive.ic2d.monitoring.data.HostObject;
import org.objectweb.proactive.ic2d.monitoring.dnd.DragAndDrop;
import org.objectweb.proactive.ic2d.monitoring.extpoints.IActionExtPoint;
import org.objectweb.proactive.ic2d.monitoring.figures.HostFigure;
import org.objectweb.proactive.ic2d.monitoring.views.MonitoringView;

public class HostListener implements MouseListener, MouseMotionListener {

	private ActionRegistry registry;

	private HostObject host;
	private HostFigure figure;

	private DragAndDrop dnd;
	private DragHost dragHost;
	
	public HostListener(HostObject host, HostFigure figure, MonitoringView monitoringView) {
		this.registry = monitoringView.getGraphicalViewer().getActionRegistry();
		this.host = host;
		this.figure = figure;
		this.dnd = monitoringView.getDragAndDrop();
		this.dragHost = monitoringView.getDragHost();
	}

	public void mouseDoubleClicked(MouseEvent me) { /* Do nothing */ }

	public void mousePressed(MouseEvent me) {
		if(me.button == 1){
			dnd.reset();
			dragHost.mousePressed(me);
		}
		else if(me.button == 3) {
			// Monitor a new host
			registry.getAction(NewHostAction.NEW_HOST).setEnabled(false);
			
			// Set depth control
			registry.getAction(SetDepthAction.SET_DEPTH).setEnabled(false);
				
			// Refresh
			registry.getAction(RefreshAction.REFRESH).setEnabled(false);
			
			// Set time to refresh
			registry.getAction(SetTTRAction.SET_TTR).setEnabled(false);
			
			// Look for new JVM
			RefreshHostAction refreshHostAction = (RefreshHostAction)registry.getAction(RefreshHostAction.REFRESH_HOST);
			refreshHostAction.setHost(host);
			refreshHostAction.setEnabled(true);
			
			// Look for new Nodes
			registry.getAction(RefreshJVMAction.REFRESH_JVM).setEnabled(false);
			
			// Look for new Active Objects
			registry.getAction(RefreshNodeAction.REFRESH_NODE).setEnabled(false);
			
			// Stop monitoring this host
			StopMonitoringAction stopMonitoringAction = (StopMonitoringAction)registry.getAction(StopMonitoringAction.STOP_MONITORING);
			stopMonitoringAction.setObject(host);
			stopMonitoringAction.setEnabled(true);
			
			// Kill VM
			registry.getAction(KillVMAction.KILLVM).setEnabled(false);
			
			// Set update frequence...
			registry.getAction(SetUpdateFrequenceAction.SET_UPDATE_FREQUENCE).setEnabled(false);
			

			// Vertical Layout
			VerticalLayoutAction verticalLayoutAction = (VerticalLayoutAction)registry.getAction(VerticalLayoutAction.VERTICAL_LAYOUT);
			verticalLayoutAction.setHost(figure);
			if(figure.isVerticalLayout())
				verticalLayoutAction.setChecked(true);
			verticalLayoutAction.setEnabled(true);
			
			// Vertical Layout
			HorizontalLayoutAction horizontalLayoutAction = (HorizontalLayoutAction)registry.getAction(HorizontalLayoutAction.HORIZONTAL_LAYOUT);
			horizontalLayoutAction.setHost(figure);
			if(figure.isVerticalLayout())
				horizontalLayoutAction.setChecked(false);
			horizontalLayoutAction.setEnabled(true);
			
			// Manual handling of an action for timer snapshot ... needs improvement
			IAction anAction = registry.getAction("Get timer snapshot");
			if ( anAction != null ){
				((IActionExtPoint)anAction).setAbstractDataObject(this.host);
				anAction.setText("Gather Stats from Host");
				anAction.setEnabled(true);
			}
		}
	}

	public void mouseReleased(MouseEvent me) {
		dnd.reset();
		dragHost.mouseReleased(me);
	}
	
	//---- MouseMotionListener 

	public void mouseEntered(MouseEvent me) {		
		if(dnd.getSource()!=null)
			dnd.refresh(null);
	}

	public void mouseExited(MouseEvent me) {
		if(dnd.getSource()!=null)
			dnd.refresh(null);
	}

	public void mouseDragged(MouseEvent me) {
		dragHost.mouseDragged(me);
	}
	
	public void mouseHover(MouseEvent me) { /* Do nothing */ }

	public void mouseMoved(MouseEvent me) {	/* Do nothing */ }

}
