package org.tomcatlogwatcher.dataaccess;

import org.tomcatlogwatcher.utility.AppLogger;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnector {

    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String JDBC_URL = "jdbc:h2:mem:ac_log;DB_CLOSE_DELAY=-1;IGNORECASE=TRUE";
    private static final String UNAME = "sa";
    private static final String PASS = "";

    public static void initDBConnector() throws Exception {
        try {
            Class.forName(DB_DRIVER);
        } catch (Throwable e) {
            AppLogger.logFatal("DBConnector.initDBConnector: ", e);
            throw e;
        }
    }

    public static Connection getConnection() throws Exception {
        try {
            return DriverManager.getConnection(JDBC_URL, UNAME, PASS);
        } catch (Throwable e) {
            AppLogger.logFatal("DBConnector.getConnection: ", e);
            throw e;
        }
    }

    public static void closeConnection(Connection conn) throws Exception {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Throwable e) {
            AppLogger.logFatal("DBConnector.getConnection: ", e);
            throw e;
        }
    }

    public static void closeDbObject(AutoCloseable... closeableObjs) throws Exception {
        try {
            for(AutoCloseable closeable : closeableObjs) {
                if (closeable != null) {
                    if(closeable instanceof Connection) {
                        closeConnection((Connection) closeable);
                    } else {
                        closeable.close();
                    }
                }
            }
        } catch (Throwable e) {
            AppLogger.logFatal("Error in DBConnector.closeConnection()", e);
            throw e;
        }
    }


    public static void rollback(Connection conn) throws Exception {
        try {
            if (conn != null && !conn.isClosed()) conn.rollback();
        } catch (Throwable e) {
            AppLogger.logFatal("Error in DBConnector.rollback()", e);
            throw e;
        }
    }
}

