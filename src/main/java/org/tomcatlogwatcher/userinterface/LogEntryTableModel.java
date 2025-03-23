package org.tomcatlogwatcher.userinterface;

import lombok.Getter;
import lombok.Setter;
import org.tomcatlogwatcher.dto.LogEntryDTO;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class LogEntryTableModel extends AbstractTableModel {
    private final List<LogEntryDTO> logEntries;
    @Setter
    private String[] columnNames = null;
    @Setter
    @Getter
    private List<String> columnApacheValue = null;

    public LogEntryTableModel(List<LogEntryDTO> logEntries) {
        this.logEntries = logEntries;
    }

    @Override
    public int getRowCount() {
        return logEntries.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        LogEntryDTO logEntry = logEntries.get(rowIndex);
        return logEntry.getValueByApachePlaceholder(getColumnValue(columnIndex));
    }

    private String getColumnValue(int columnIndex) {
        return columnApacheValue.get(columnIndex);
    }
}

