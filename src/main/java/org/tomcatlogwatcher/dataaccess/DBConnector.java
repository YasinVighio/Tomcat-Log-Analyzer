package org.tomcatlogwatcher.dataaccess;

import org.tomcatlogwatcher.utility.AppLogger;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnector {

    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String JDBC_URL = "jdbc:h2:mem:ac_log;DB_CLOSE_DELAY=-1";
    private static final String UNAME = "sa";
    private static final String PASS = "";

    public static Connection getConnection() throws Exception {
        Class.forName(DB_DRIVER);
        return DriverManager.getConnection(JDBC_URL, UNAME, PASS);
    }

    public static void closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception e) {
            AppLogger.logSevere("Error in DBConnector.closeConnection()", e);
        }
    }

    public static void rollback(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) conn.rollback();
        } catch (Exception e) {
            AppLogger.logSevere("Error in DBConnector.rollback()", e);
        }
    }
}

