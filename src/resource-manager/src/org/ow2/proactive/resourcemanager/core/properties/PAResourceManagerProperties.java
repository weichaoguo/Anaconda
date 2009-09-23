/*
 * ################################################################
 *
 * ProActive: The Java(TM) library for Parallel, Distributed,
 *            Concurrent computing with Security and Mobility
 *
 * Copyright (C) 1997-2009 INRIA/University of Nice-Sophia Antipolis
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
package org.ow2.proactive.resourcemanager.core.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.objectweb.proactive.annotation.PublicAPI;
import org.objectweb.proactive.core.config.PAProperties.PAPropertiesType;


/**
 * PAResourceManagerProperties contains all ProActive Resource Manager properties.
 * 
 * You must use provide methods in order to get this RM properties.
 * 
 * @author The ProActiveTeam
 * @since ProActive 4.0
 *
 * $Id$
 */
@PublicAPI
public enum PAResourceManagerProperties {

    /* ***************************************************************** */
    /* ********************** RMCORE PROPERTIES ********************* */
    /* ***************************************************************** */

    /** name of the ProActive Node containing RM's active objects */
    RM_NODE_NAME("pa.rm.node.name", PAPropertiesType.STRING),

    /** ping frequency in ms used by node source for keeping a watch on handled nodes */
    RM_NODE_SOURCE_PING_FREQUENCY("pa.rm.node.source.ping.frequency", PAPropertiesType.INTEGER),

    /** ping frequency used by resource manager to ping connected clients (in ms) */
    RM_CLIENT_PING_FREQUENCY("pa.rm.client.ping.frequency", PAPropertiesType.INTEGER),

    /**  Timeout in ms for selection script execution */
    RM_SELECT_SCRIPT_TIMEOUT("pa.rm.select.script.timeout", PAPropertiesType.INTEGER),

    /**  Timeout in ms for node lookup */
    RM_NODELOOKUP_TIMEOUT("pa.rm.nodelookup.timeout", PAPropertiesType.INTEGER),

    /** GCM application template file path, used to perform GCM deployments */
    RM_GCM_TEMPLATE_APPLICATION_FILE("pa.rm.gcm.template.application.file", PAPropertiesType.STRING),

    /** name of a string contained in in the GCM Application (GCMA) XML file, that must mandatory appear
     * as a place of a GCM deployment file. 
     */
    RM_GCMD_PATH_PROPERTY_NAME("pa.rm.gcmd.path.property.name", PAPropertiesType.STRING),

    /** path to the Amazon EC2 account credentials properties file,
     *  mandatory when using the EC2 Infrastructure */
    RM_EC2_PATH_PROPERTY_NAME("pa.rm.ec2.properties", PAPropertiesType.STRING),

    /** Resource Manager home directory */
    RM_HOME("pa.rm.home", PAPropertiesType.STRING),

    /** path to the Jaas configuration file which defines what modules are available for
     * internal authentication */
    RM_AUTH_JAAS_PATH("pa.rm.auth.jaas.path", PAPropertiesType.STRING),

    /** path to the private key file which is used to decrypt credentials passed to the jaas module */
    RM_AUTH_PRIVKEY_PATH("pa.rm.auth.privkey.path", PAPropertiesType.STRING),

    /** path to the public key file which is used to encrypt credentials for authentication */
    RM_AUTH_PUBKEY_PATH("pa.rm.auth.pubkey.path", PAPropertiesType.STRING),

    /** Resource Manager authentication method */
    RM_LOGIN_METHOD("pa.rm.authentication.loginMethod", PAPropertiesType.STRING),

    /** Resource Manager ldap configuration file */
    RM_LDAP_CONFIG("pa.rm.ldap.config.path", PAPropertiesType.STRING),

    /** Resource Manager login file name */
    RM_LOGIN_FILE("pa.rm.defaultloginfilename", PAPropertiesType.STRING),

    /** Resource Manager group file name */
    RM_GROUP_FILE("pa.rm.defaultgroupfilename", PAPropertiesType.STRING),

    /** Name of the JMX MBean for the RM */
    RM_JMX_CONNECTOR_NAME("pa.rm.jmx.connectorname", PAPropertiesType.STRING),

    /** Port of the JMX service. Random if not set */
    RM_JMX_PORT("pa.rm.jmx.port", PAPropertiesType.INTEGER),

    /** Resource Manager node source infrastructures file*/
    RM_NODESOURCE_INFRASTRUCTURE_FILE("pa.rm.nodesource.infrastructures", PAPropertiesType.STRING),

    /** Resource Manager node source policies file*/
    RM_NODESOURCE_POLICY_FILE("pa.rm.nodesource.policies", PAPropertiesType.STRING),

    /** Max number of threads in node source for parallel task execution */
    RM_NODESOURCE_MAX_THREAD_NUMBER("pa.rm.nodesource.maxthreadnumber", PAPropertiesType.INTEGER),

    /** Path to the Resource Manager credentials for adding local nodes */
    RM_CREDS("pa.rm.credentials", PAPropertiesType.STRING);

    /* ***************************************************************************** */
    /* ***************************************************************************** */
    /** Default properties file for the RM configuration */
    private static final String DEFAULT_PROPERTIES_FILE;

    private static final boolean fileLoaded;

    static {
        String propertiesPath = "config/PAResourceManagerProperties.ini";
        if (System.getProperty("pa.rm.properties.filepath") != null) {
            propertiesPath = System.getProperty("pa.rm.properties.filepath");
        }
        if (!new File(propertiesPath).isAbsolute()) {
            propertiesPath = System.getProperty(RM_HOME.key) + File.separator + propertiesPath;
        }
        DEFAULT_PROPERTIES_FILE = propertiesPath;
        fileLoaded = new File(propertiesPath).exists();
    }
    /** memory entity of the properties file. */
    private static Properties prop = null;
    /** Key of the specific instance. */
    private String key;
    /** value of the specific instance. */
    private PAPropertiesType type;

    /**
     * Create a new instance of PAResourceManagerProperties
     *
     * @param str the key of the instance.
     * @param type the real java type of this instance.
     */
    PAResourceManagerProperties(String str, PAPropertiesType type) {
        this.key = str;
        this.type = type;
    }

    /**
     * Get the key.
     *
     * @return the key.
     */
    public String getKey() {
        return key;
    }

    /**
     * Set a the value of this property to the given one.
     *
     * @param value the new value to set.
     */
    public void updateProperty(String value) {
        getProperties(DEFAULT_PROPERTIES_FILE);
        prop.setProperty(key, value);
    }

    /**
     * Set the user java properties to the PASchedulerProperties.<br/>
     * User properties are defined using the -Dname=value in the java command.
     */
    private static void setUserJavaProperties() {
        for (Object o : prop.keySet()) {
            String s = System.getProperty((String) o);
            if (s != null) {
                prop.setProperty((String) o, s);
            }
        }
    }

    /**
     * Get the properties map or load it if needed.
     * 
     * @param filename the new file to be loaded.
     * @return the properties map corresponding to the default property file.
     */
    private static Properties getProperties(String filename) {
        if (prop == null) {
            prop = new Properties();
            try {
                prop.load(new FileInputStream(filename));
                setUserJavaProperties();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return prop;
    }

    /**
     * override properties defined in the default configuration file,
     * by properties defined in another file.
     * @param filename path of file containing some properties to override
     */
    public static void updateProperties(String filename) {
        if (fileLoaded) {
            getProperties(DEFAULT_PROPERTIES_FILE);
            Properties ptmp = new Properties();
            try {
                ptmp.load(new FileInputStream(filename));
                for (Object o : ptmp.keySet()) {
                    prop.setProperty((String) o, (String) ptmp.get(o));
                }
                setUserJavaProperties();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            getProperties(filename);
        }
    }

    /**
     * Return true if this property is set, false otherwise.
     *
     * @return true if this property is set, false otherwise.
     */
    public boolean isSet() {
        if (fileLoaded) {
            return prop.containsKey(key);
        } else {
            return false;
        }
    }

    /**
     * Returns the string to be passed on the command line
     *
     * The property surrounded by '-D' and '='
     *
     * @return the string to be passed on the command line
     */
    public String getCmdLine() {
        return "-D" + key + '=';
    }

    /**
     * Returns the value of this property as an integer.
     * If value is not an integer, an exception will be thrown.
     * 
     * @return the value of this property.
     */
    public int getValueAsInt() {
        if (fileLoaded) {
            String valueS = getValueAsString();
            try {
                int value = Integer.parseInt(valueS);
                return value;
            } catch (NumberFormatException e) {
                RuntimeException re = new IllegalArgumentException(key +
                    " is not an integer property. getValueAsInt cannot be called on this property");
                throw re;
            }
        } else {
            return 0;
        }
    }

    /**
     * Returns the value of this property as a string.
     * 
     * @return the value of this property.
     */
    public String getValueAsString() {
        if (fileLoaded) {
            return getProperties(DEFAULT_PROPERTIES_FILE).getProperty(key);
        } else {
            return "";
        }
    }

    /**
     * Returns the value of this property as a boolean.
     * If value is not a boolean, an exception will be thrown.<br>
     * The behavior of this method is the same as the {@link java.lang.Boolean#parseBoolean(String s)}. 
     * 
     * @return the value of this property.
     */
    public boolean getValueAsBoolean() {
        if (fileLoaded) {
            return Boolean.parseBoolean(getValueAsString());
        } else {
            return false;
        }
    }

    /**
     * Return the type of the given properties.
     *
     * @return the type of the given properties.
     */
    public PAPropertiesType getType() {
        return type;
    }

    /**
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return getValueAsString();
    }

    /**
     * Get the absolute path of the given path.<br/>
     * It the path is absolute, then it is returned. If the path is relative, then the RM_home directory is 
     * concatenated in front of the given string.
     *
     * @param userPath the path to check transform.
     * @return the absolute path of the given path.
     */
    public static String getAbsolutePath(String userPath) {
        if (new File(userPath).isAbsolute()) {
            return userPath;
        } else {
            return PAResourceManagerProperties.RM_HOME.getValueAsString() + File.separator + userPath;
        }
    }

}
