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
package org.objectweb.proactive.core.remoteobject;

import java.net.URI;

import org.objectweb.proactive.core.Constants;
import org.objectweb.proactive.core.ProActiveException;
import org.objectweb.proactive.core.config.ProActiveConfiguration;
import org.objectweb.proactive.core.remoteobject.exception.UnknownProtocolException;
import org.objectweb.proactive.core.util.URIBuilder;
import org.objectweb.proactive.core.util.UrlBuilder;


public class RemoteObjectHelper {

    /**
     * returns the default port set for the given protocol
     *
     * @param protocol
     *            the protocol
     * @return the default port number associated to the protocol
     * @throws UnknownProtocolException
     */
    public static int getDefaultPortForProtocol(String protocol)
        throws UnknownProtocolException {
        if (Constants.XMLHTTP_PROTOCOL_IDENTIFIER.equals(protocol)) {
            // http port could change according the availability of the default port when activated
            // so we first instantiante the factory which will set the new port if necessary
            getRemoteObjectFactory(protocol);

            if (ProActiveConfiguration.getInstance()
                                          .getProperty(Constants.PROPERTY_PA_XMLHTTP_PORT) != null) {
                return Integer.parseInt(ProActiveConfiguration.getInstance()
                                                              .getProperty(Constants.PROPERTY_PA_XMLHTTP_PORT));
            }
        } else if ((Constants.RMI_PROTOCOL_IDENTIFIER.equals(protocol)) ||
                Constants.IBIS_PROTOCOL_IDENTIFIER.equals(protocol) ||
                Constants.RMISSH_PROTOCOL_IDENTIFIER.equals(protocol)) {
            return Integer.parseInt(ProActiveConfiguration.getInstance()
                                                          .getProperty(Constants.PROPERTY_PA_RMI_PORT));
        }

        // default would be to return the RMI default port
        return -1;
    }

    /**
     * returns an url for a object to be exposed on the current host for a given
     * protocol and name
     *
     * @param protocol
     *            the protocol
     * @return the default port number associated to the protocol
     * @throws UnknownProtocolException
     */
    public static URI generateUrl(String protocol, String name)
        throws UnknownProtocolException {
        return URI.create(UrlBuilder.buildUrl(null, name, protocol,
                getDefaultPortForProtocol(protocol), true));
    }

    /**
     * returns an url for a object to be exposed on the current host for the
     * default protocol and name
     *
     * @param name
     *            the name
     * @return the URI for the given name
     */
    public static URI generateUrl(String name) {
        String protocol = ProActiveConfiguration.getInstance()
                                                .getProperty(Constants.PROPERTY_PA_COMMUNICATION_PROTOCOL);
        try {
            return URI.create(UrlBuilder.buildUrl(null, name, protocol,
                    getDefaultPortForProtocol(protocol), true));
        } catch (UnknownProtocolException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static RemoteObjectFactory getRemoteObjectFactory(String protocol)
        throws UnknownProtocolException {
        return AbstractRemoteObjectFactory.getRemoteObjectFactory(protocol);
    }

    public static RemoteObjectFactory getFactoryFromURL(URI url)
        throws UnknownProtocolException {
        url = expandURI(url);
        return getRemoteObjectFactory(url.getScheme());
    }

    public static URI[] list(URI url) throws ProActiveException {
        return getFactoryFromURL(url).list(expandURI(url));
    }

    public static URI expandURI(URI uri) {
        if (uri.getScheme() == null) {
            int port = uri.getPort();
            if (port == -1) {
                uri = URIBuilder.buildURIFromProperties(uri.getHost(),
                        uri.getPath());
            } else {
                uri = URIBuilder.setProtocol(uri,
                        ProActiveConfiguration.getInstance()
                                              .getProperty(Constants.PROPERTY_PA_COMMUNICATION_PROTOCOL));
            }
            System.out.println("rmo url expanded to " + uri.toString());
        }
        return uri;
    }

    public static RemoteRemoteObject register(RemoteObject target, URI url,
        boolean replacePreviousBinding) throws ProActiveException {
        return getFactoryFromURL(url)
                   .register(target, expandURI(url), replacePreviousBinding);
    }

    public static void unregister(URI url) throws ProActiveException {
        getFactoryFromURL(url).unregister(expandURI(url));
    }

    public static RemoteObject lookup(URI url) throws ProActiveException {
        return getFactoryFromURL(url).lookup(expandURI(url));
    }
}
