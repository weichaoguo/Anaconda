package org.ow2.proactive.resourcemanager.gui.data;

import java.io.Serializable;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.ow2.proactive.resourcemanager.gui.actions.ConnectDeconnectResourceManagerAction;
import org.ow2.proactive.resourcemanager.gui.interfaces.RMCoreEventListener;
import org.ow2.proactive.resourcemanager.gui.views.ResourceExplorerView;


public class RMCoreListenerImpl implements RMCoreEventListener, Serializable {

    private Shell shell = null;

    public RMCoreListenerImpl(Shell s) {
        shell = s;
    }

    public void imKilledEvent() {
        // TODO Auto-generated method stub

    }

    public void imShutDownEvent() {
        Display.getDefault().asyncExec(new Runnable() {
            public void run() {
                MessageDialog.openInformation(shell, "shutdown", "Resource manager " +
                    RMStore.getInstance().getURL() + " has been shutdown, now disconnect.");
                //ConnectDeconnectResourceManagerAction.getInstance().run();
                ConnectDeconnectResourceManagerAction.getInstance().disconnectWithoutConfirm();
            }
        });
    }

    public void imShuttingDownEvent() {
        // TODO Auto-generated method stub	
    }

    public void imStartedEvent() {
        // TODO Auto-generated method stub

    }
}