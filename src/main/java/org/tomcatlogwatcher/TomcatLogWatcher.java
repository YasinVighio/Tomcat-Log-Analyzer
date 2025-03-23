package org.tomcatlogwatcher;

import org.tomcatlogwatcher.core.PropManager;
import org.tomcatlogwatcher.userinterface.AccessLogViewer;
import org.tomcatlogwatcher.utility.AppLogger;

import javax.swing.*;


/**
 *
 * @author Yasin
 */
public class TomcatLogWatcher {

    private static final String APP_NAME = "Tomcat Log Analyzer";

    public static void main(String[] args) {
        try {
            AppLogger.initLogger();
            PropManager.initProperties();
            initApp();
        } catch (Exception e) {
            AppLogger.logSevere("Error initializing app", e);
        }

    }

    public static void initApp(){
        AccessLogViewer accessLogViewer = new AccessLogViewer();
        accessLogViewer.setTitle(APP_NAME);
        accessLogViewer.setVisible(true);
        accessLogViewer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
