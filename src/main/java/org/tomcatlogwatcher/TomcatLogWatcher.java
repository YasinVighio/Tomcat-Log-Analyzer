package org.tomcatlogwatcher;

import org.tomcatlogwatcher.core.PropManager;
import org.tomcatlogwatcher.dataaccess.DBConnector;
import org.tomcatlogwatcher.userinterface.AccessLogViewScreen;
import org.tomcatlogwatcher.utility.AppLogger;

import javax.swing.*;


/**
 *
 * @author Yasin
 */
public class TomcatLogWatcher {

    private static final String APP_NAME = "Tomcat Log Analyzer";

    public static boolean isDefaultJVMTimeZoneSet = false;

    public static void main(String[] args) {
        try {
            initApp();
            initUI();
        } catch (Exception e) {
            AppLogger.logSevere("Error initializing app", e);
        }

    }

    public static void initApp() throws Exception {
        AppLogger.initLogger();
        PropManager.initProperties();
        DBConnector.initDBConnector();
    }

    public static void initUI(){
        AccessLogViewScreen accessLogViewer = new AccessLogViewScreen();
        accessLogViewer.setTitle(APP_NAME);
        accessLogViewer.setVisible(true);
        accessLogViewer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
