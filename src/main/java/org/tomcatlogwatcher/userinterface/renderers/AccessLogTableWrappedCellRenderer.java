package org.tomcatlogwatcher.userinterface.renderers;

import javax.swing.*;
import java.awt.*;

public class AccessLogTableWrappedCellRenderer extends JTextArea implements AccessLogTableCellRenderer {

    private String dateFormat;

    private Class<?> clazz;


    public AccessLogTableWrappedCellRenderer() {
        setLineWrap(true);
        setWrapStyleWord(true);
        setFont(new Font("Arial", Font.PLAIN, 18));
        setOpaque(true);
        setEditable(false);
        setAlignmentX(CENTER_ALIGNMENT);
        setAlignmentY(CENTER_ALIGNMENT);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setText(value != null ? value.toString() : "");

        if (isSelected) {
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());
        } else {
            setBackground(table.getBackground());
            setForeground(table.getForeground());
        }

        return this;
    }

    @Override
    public void setClassType(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Class<?> getClassType() {
        return this.clazz;
    }

    @Override
    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public String getDateFormat() {
        return this.dateFormat;
    }
}
