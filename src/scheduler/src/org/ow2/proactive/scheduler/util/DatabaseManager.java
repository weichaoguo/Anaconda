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
package org.ow2.proactive.scheduler.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


/**
 * Initialize a database manager since a property file. The file must contain
 * "driver", "protocol", "db_path" (database path), "db_name" (database name),
 * "user" and "password" properties.
 *
 * @author The ProActive Team
 */
public class DatabaseManager {

    private static DatabaseManager instance = null;
    private String driver = null;
    private String protocol = null;
    private String databasePath = null;
    private String databaseName = null;
    private String user = null;
    private String password = null;

    /**
     * The default constructor.
     *
     * @throws IOException
     */
    private DatabaseManager(String configFile) throws IOException {
        Properties props = new Properties();
        BufferedInputStream bis = null;

        try {
            bis = new BufferedInputStream(new FileInputStream(configFile));
            props.load(bis);
            if (!props.containsKey("driver") || "".equals((driver = props.getProperty("driver")))) {
                throw new RuntimeException("Driver property is not defined in " + configFile +
                    " ('driver=...'");
            }
            if (!props.containsKey("protocol") || "".equals((protocol = props.getProperty("protocol")))) {
                throw new RuntimeException("Protocol property is not defined in " + configFile +
                    " ('protocol=...'");
            }
            if (!props.containsKey("db_path")) {
                throw new RuntimeException("Driver property is not defined in " + configFile +
                    " ('db_path=...'");
            }
            databasePath = props.getProperty("db_path");
            if (!props.containsKey("db_name") || "".equals((databaseName = props.getProperty("db_name")))) {
                throw new RuntimeException("Database name property is not defined in " + configFile +
                    " ('db_name=...'");
            }
            if (!props.containsKey("user") || "".equals((user = props.getProperty("user")))) {
                throw new RuntimeException("User property is not defined in " + configFile + " ('user=...'");
            }
            if (!props.containsKey("password")) {
                throw new RuntimeException("Password property is not defined in " + configFile +
                    " ('password=...'");
            }
            password = props.getProperty("password");
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * To load the database driver, connect to the database and obtain a
     * connection.
     *
     * @param create true if the database must be created.
     * @return a connection to the database
     */
    public Connection connect(boolean create) throws SQLException {
        if (!"".equals(databasePath)) {
            databasePath += "/";
        }
        String url = protocol + databasePath + databaseName + ((create) ? ";create=true" : ";");

        System.out.println("[SCHEDULER-DATABASE] url=" + url);

        try {
            Class.forName(driver).newInstance();

            return DriverManager.getConnection(url, user, password);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * To disconnect the database and shutdown it.
     *
     * @return true only if the database shutdown successfully
     */
    public boolean disconnect() {
        try {
            DriverManager.getConnection(protocol + ";shutdown=true");
        } catch (SQLException e) {
            return true;
        }

        return false;
    }

    /**
     * Return the current instance, if the instance is null then this method
     * will create a new instance before returning it.
     *
     * @return the instance
     */
    public static DatabaseManager getInstance(String configFile) {
        try {
            instance = new DatabaseManager(configFile);

            return instance;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}