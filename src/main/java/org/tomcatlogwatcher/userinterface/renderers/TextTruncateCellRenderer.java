package org.tomcatlogwatcher.userinterface.renderers;

import org.tomcatlogwatcher.utility.Utils;

import javax.swing.*;
import java.awt.Component;
import java.util.List;

public class TextTruncateCellRenderer extends AccessLogTableWrappedCellRenderer {

    private List<String> truncatedTexts;

    public TextTruncateCellRenderer(List<String> textToTruncate) {
        this.truncatedTexts = textToTruncate;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (value instanceof String) {
            String str = (String) value;
            if(Utils.areStringsValid(truncatedTexts)) {
                for(String truncatedText : truncatedTexts) {
                    if(truncatedText.contains("*")) {
                        String stringToTruncate = truncatedText.substring(0, truncatedText.indexOf("*"));
                        if(str.contains(stringToTruncate)) {
                            str = str.substring(0, str.indexOf(stringToTruncate));
                        }
                    } else {
                        str = str.replace(truncatedText, "");
                    }
                }
            }
            setText(str);
        }
        return this;
    }
}
