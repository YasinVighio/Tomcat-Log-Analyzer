package org.tomcatlogwatcher.utility;

import org.tomcatlogwatcher.data.Constants;
import org.tomcatlogwatcher.core.PropManager;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;


public class UIUtils {

    public static boolean validateDQL(String dql) {
        if(Utils.areStringsValid(dql)){
            return dql.toUpperCase().startsWith("SELECT");
        }
        return false;
    }

    public static void setOutputText(String outputText, boolean isSuccessMsg, JTextPane textPane) {
        try {
            String color = isSuccessMsg ? "blue" : "red";
            String htmlText = String.format(
                    "<html><p style='color: %s; font-weight: bold; font-size: 14px;'>%s</p></html>",
                    color,
                    outputText
            );

            textPane.setContentType("text/html");
            textPane.setText(htmlText);
        } catch (Exception e) {
            AppLogger.logSevere("Error in UIUtils.setOutputText", e);
        }
    }


}
