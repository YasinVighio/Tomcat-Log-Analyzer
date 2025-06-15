package org.tomcatlogwatcher.userinterface.models;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class LogEntryTableModel extends DefaultTableModel {
    private List<Class<?>> columnTypes;

    public LogEntryTableModel(String[] columnNames, int rowCount) {
        super(columnNames, rowCount);
    }

    public void setColumnTypes(List<Class<?>> columnTypes) {
        this.columnTypes = columnTypes;
    }

    public List<Class<?>> getColumnTypes() {
        return columnTypes;
    }

    @Override
    public Object getValueAt(int row, int column) {
        return super.getValueAt(row, column);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false; // no cell is editable
    }
}

