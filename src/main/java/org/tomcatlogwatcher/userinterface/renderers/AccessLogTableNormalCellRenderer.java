package org.tomcatlogwatcher.userinterface.renderers;

import org.tomcatlogwatcher.utility.DateUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

import java.awt.*;
import java.util.Date;

public class AccessLogTableNormalCellRenderer extends DefaultTableCellRenderer implements AccessLogTableCellRenderer {

    private String dateFormat;

    private Class<?> clazz;

    public AccessLogTableNormalCellRenderer(){
        super();
        setHorizontalAlignment(SwingConstants.CENTER);
    }


    public Component getTableCellRendererComponent(JTable table,
                                                   Object value, boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        if( value instanceof Date) {
            value = DateUtil.convertDateToString((Date) value, this.dateFormat);
        }
        return super.getTableCellRendererComponent(table, value, isSelected,
                hasFocus, row, column);
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
