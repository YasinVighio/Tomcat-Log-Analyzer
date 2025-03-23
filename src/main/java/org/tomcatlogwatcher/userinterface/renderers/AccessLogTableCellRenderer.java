package org.tomcatlogwatcher.userinterface.renderers;

import javax.swing.table.TableCellRenderer;

public interface AccessLogTableCellRenderer extends TableCellRenderer {
    public void setClassType(Class<?> clazz);
    public Class<?> getClassType();
    public void setDateFormat(String dateFormat);
    public String getDateFormat();
}
