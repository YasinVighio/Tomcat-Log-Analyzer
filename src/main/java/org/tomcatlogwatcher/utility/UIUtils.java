package org.tomcatlogwatcher.utility;


import org.tomcatlogwatcher.dto.HTMLTextDTO;

import javax.swing.*;
import java.awt.*;


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

            HTMLTextDTO htmlText = HTMLTextDTO.builder()
                    .textColor(color)
                    .textFontSize("14px")
                    .textWeight("bold")
                    .textStyle("Arial")
                    .textParagraph(outputText)
                    .build();

            textPane.setContentType("text/html");
            textPane.setText(htmlText.getGeneratedText());
        } catch (Exception e) {
            AppLogger.logSevere("Error in UIUtils.setOutputText", e);
        }
    }

    public static String openFileDialog(JFrame parent){
        String filePath = "";
        try{
            FileDialog fd = new FileDialog(parent, "Open File");
            fd.setVisible(true);
            if(Utils.areStringsValid(fd.getFile(), fd.getDirectory())){
                filePath = fd.getDirectory()+fd.getFile();
            }
        } catch (Exception e){
            AppLogger.logSevere("Error AccessLogViewScreen.openFileDialog", e);
        }
        return filePath;
    }


}
