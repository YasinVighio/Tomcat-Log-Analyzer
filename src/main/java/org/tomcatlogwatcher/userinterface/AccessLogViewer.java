package org.tomcatlogwatcher.userinterface;

import org.tomcatlogwatcher.core.ApacheLoggingConstants;
import org.tomcatlogwatcher.core.Constants;
import org.tomcatlogwatcher.core.LogWatcher;
import org.tomcatlogwatcher.core.PropManager;
import org.tomcatlogwatcher.dto.AccessLogDTO;
import org.tomcatlogwatcher.dto.ActionDTO;
import org.tomcatlogwatcher.userinterface.renderers.AccessLogTableCellRenderer;
import org.tomcatlogwatcher.userinterface.renderers.AccessLogTableNormalCellRenderer;
import org.tomcatlogwatcher.userinterface.adapters.AccessLogTableMouseAdapter;
import org.tomcatlogwatcher.userinterface.renderers.AccessLogTableWrappedCellRenderer;
import org.tomcatlogwatcher.utility.AppLogger;
import org.tomcatlogwatcher.utility.DateUtil;
import org.tomcatlogwatcher.utility.UIUtils;
import org.tomcatlogwatcher.utility.Utils;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 *
 * @author Yasin
 */
public class AccessLogViewer extends javax.swing.JFrame {

    private static RowFilter<TableModel, Object> tableFilter = null;
    private static TableRowSorter<TableModel> tableSorter = null;
    private Date accessLogDate = null;


    public AccessLogViewer() {
        initComponents();
    }

    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem2 = new javax.swing.JCheckBoxMenuItem();
        fileNameField = new javax.swing.JTextField();
        patternField = new javax.swing.JTextField();
        searchField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        pickFileBtn = new javax.swing.JButton();
        processFileBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        accessLogTbl = new javax.swing.JTable();
        columnSelector = new javax.swing.JComboBox<>();
        clearFilterBtn = new javax.swing.JButton();
        criteriaSelector = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        disjunctionSelector = new javax.swing.JComboBox<>();
        searchButton = new javax.swing.JButton();
        reqMethodLbl = new javax.swing.JLabel();
        reqMethodSelector = new javax.swing.JComboBox<>();

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        jCheckBoxMenuItem2.setSelected(true);
        jCheckBoxMenuItem2.setText("jCheckBoxMenuItem2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        fileNameField.setFont(new java.awt.Font("Arial", Font.PLAIN, 14));
        fileNameField.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 416;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 6, 0, 0);
        getContentPane().add(fileNameField, gridBagConstraints);

        patternField.setFont(new java.awt.Font("Arial", Font.PLAIN, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 416;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 6, 0, 0);
        getContentPane().add(patternField, gridBagConstraints);

        searchField.setFont(new java.awt.Font("Arial", Font.PLAIN, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 416;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 6, 0, 0);
        getContentPane().add(searchField, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Arial", Font.BOLD, 14));
        jLabel1.setText("Value");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(25, 6, 0, 0);
        getContentPane().add(jLabel1, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Arial", Font.BOLD, 14));
        jLabel2.setText("Pattern");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(19, 6, 0, 0);
        getContentPane().add(jLabel2, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Arial", Font.BOLD, 14));
        jLabel3.setText("File");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 16;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 6, 0, 0);
        getContentPane().add(jLabel3, gridBagConstraints);

        pickFileBtn.setFont(new java.awt.Font("Arial", Font.BOLD, 14));
        pickFileBtn.setText("PICK FILE");
        pickFileBtn.addActionListener(this::pickFileBtnActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 14;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 36;
        gridBagConstraints.ipady = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 18, 0, 0);
        getContentPane().add(pickFileBtn, gridBagConstraints);

        processFileBtn.setFont(new java.awt.Font("Arial", Font.BOLD, 14));
        processFileBtn.setText("PROCESS FILE");
        processFileBtn.setEnabled(false);
        processFileBtn.addActionListener(this::processFileBtnActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 14;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipady = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 18, 0, 0);
        getContentPane().add(processFileBtn, gridBagConstraints);

        accessLogTbl.setFont(new java.awt.Font("Arial", Font.PLAIN, 18));
        accessLogTbl.setRowHeight(50);
        jScrollPane1.setViewportView(accessLogTbl);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 29;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1010;
        gridBagConstraints.ipady = 472;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 6, 6, 6);
        getContentPane().add(jScrollPane1, gridBagConstraints);

        columnSelector.setFont(new java.awt.Font("Arial", Font.PLAIN, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 115;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 7, 0, 0);
        getContentPane().add(columnSelector, gridBagConstraints);

        clearFilterBtn.setFont(new java.awt.Font("Arial", Font.BOLD, 14));
        clearFilterBtn.setText("CLEAR FILTER");
        clearFilterBtn.addActionListener(this::clearFilterBtnActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 20;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = 27;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 12, 0, 0);
        getContentPane().add(clearFilterBtn, gridBagConstraints);

        criteriaSelector.setFont(new java.awt.Font("Arial", Font.BOLD, 14));
        criteriaSelector.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "LIKE", "GT", "LT", "EQ", "NE" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 12, 0, 0);
        getContentPane().add(criteriaSelector, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Arial", Font.BOLD, 14));
        jLabel4.setText("Criteria");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.ipadx = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(21, 6, 0, 0);
        getContentPane().add(jLabel4, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Arial", Font.BOLD, 14));
        jLabel5.setText("Column");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(21, 6, 0, 0);
        getContentPane().add(jLabel5, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Arial", Font.BOLD, 14));
        jLabel6.setText("Disjunction");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.ipadx = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(21, 12, 0, 0);
        getContentPane().add(jLabel6, gridBagConstraints);

        disjunctionSelector.setFont(new java.awt.Font("Arial", Font.BOLD, 14));
        disjunctionSelector.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "OR", "AND" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 6, 0, 0);
        getContentPane().add(disjunctionSelector, gridBagConstraints);

        searchButton.setFont(new java.awt.Font("Arial", Font.BOLD, 14));
        searchButton.setText("SEARCH");
        searchButton.addActionListener(this::searchButtonActionPerformed);

        customizeAccessLogTable();

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = 70;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 49, 0, 0);
        getContentPane().add(searchButton, gridBagConstraints);

        reqMethodLbl.setFont(new java.awt.Font("Arial", Font.BOLD, 14));
        reqMethodLbl.setText("Method");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.ipadx = 39;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(21, 12, 0, 0);
        getContentPane().add(reqMethodLbl, gridBagConstraints);

        reqMethodSelector.setFont(new java.awt.Font("Arial", Font.BOLD, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 6, 0, 0);
        getContentPane().add(reqMethodSelector, gridBagConstraints);

        pack();
    }

    private void pickFileBtnActionPerformed(java.awt.event.ActionEvent evt) {
        String newFile = openFileDialog(this);
        fileNameField.setText(newFile);
        processFileBtn.setEnabled(Utils.areStringsValid(newFile));
    }

    private void processFileBtnActionPerformed(java.awt.event.ActionEvent evt) {
        String pattern = Utils.areStringsValid(patternField.getText()) ? patternField.getText() : PropManager.getDefaultPattern();
        ActionDTO actionDTO = LogWatcher.readAccessFile(fileNameField.getText(), pattern);
        if (actionDTO.getIsSuccessful()) {

            AccessLogDTO accessLogDTO = (AccessLogDTO) actionDTO.getData();
            LogEntryTableModel logEntryTableModel = new LogEntryTableModel(accessLogDTO.getLogEntries());
            logEntryTableModel.setColumnNames(accessLogDTO.getHeaders().toArray(new String[0]));
            logEntryTableModel.setColumnApacheValue(accessLogDTO.getColumnApacheValues());

            //set columns
            columnSelector.setModel(new DefaultComboBoxModel<>(accessLogDTO.getHeaders().toArray(new String[0])));

            //set req methods

            accessLogDTO.getRequestMethods().add(0, "ANY");
            reqMethodSelector.setModel(new DefaultComboBoxModel<>(accessLogDTO.getRequestMethods().toArray(new String[0])));

            this.accessLogDate = accessLogDTO.getAccessLogDate();

            this.accessLogTbl.setModel(logEntryTableModel);

            tableSorter = new TableRowSorter<>(accessLogTbl.getModel());
            accessLogTbl.setRowSorter(tableSorter);

            for (int i = 0; i < accessLogTbl.getColumnCount(); i++) {

                String apacheColumnVal = accessLogDTO.getColumnApacheValues().get(i);

                Class<?> logDataType = ApacheLoggingConstants.LOG_DATA_TYPE_MAP.get(apacheColumnVal);

                setAccessLogTableCellRenderer(apacheColumnVal, logDataType, i);
            }
        } else {
            JOptionPane.showMessageDialog(this, actionDTO.getMessage(), "Error Message", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFilterBtnActionPerformed(java.awt.event.ActionEvent evt) {
        tableFilter = null;
        tableSorter.setRowFilter(null);
        searchField.setText("");
        reqMethodSelector.setSelectedIndex(0);
    }

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {
        filterTable();
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

    private void filterTable() {
        String text = searchField.getText();

        String selectedColumnName = (String) columnSelector.getSelectedItem();

        int selectedColumnModelIndex = getColumnIndexByColumnName(selectedColumnName, true, false);

        int selectedColumnIndex = getColumnIndexByColumnName(selectedColumnName, false, false);

        String selectedCriteria = (String) criteriaSelector.getSelectedItem();

        RowFilter<TableModel, Object> columnFilter = null;
        if (Utils.areStringsValid(text)) {
            if (Objects.equals(selectedCriteria, "LIKE")) {
                String regex = "(?i)" + text.replace("*", ".*");
                columnFilter = RowFilter.regexFilter(regex, selectedColumnModelIndex);
            } else {
                AccessLogTableCellRenderer cellRenderer = (AccessLogTableCellRenderer) accessLogTbl.getColumnModel().getColumn(selectedColumnIndex).getCellRenderer();
                if (cellRenderer.getClassType().equals(Date.class)) {
                    try {
                        Date dateTime = DateUtil.getDateFromInputDateString(text, this.accessLogDate);
                        columnFilter = RowFilter.dateFilter(UIUtils.getComparisonType(selectedCriteria), dateTime, selectedColumnModelIndex);
                    } catch (Exception pe) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid number or date. Such as: " + String.join(", ", PropManager.getAllowedDateInputFormats()));
                    }
                } else if (cellRenderer.getClassType().equals(Double.class)) {
                    Double numericValue = Double.parseDouble(text);
                    columnFilter = RowFilter.numberFilter(UIUtils.getComparisonType(selectedCriteria), numericValue, selectedColumnModelIndex);
                } else if (cellRenderer.getClassType().equals(String.class)) {
                    columnFilter = RowFilter.regexFilter(text, selectedColumnModelIndex);
                } else if (cellRenderer.getClassType().equals(Integer.class)) {
                    Integer numericValue = Integer.parseInt(text);
                    columnFilter = RowFilter.numberFilter(UIUtils.getComparisonType(selectedCriteria), numericValue, selectedColumnModelIndex);
                }
            }

            if (columnFilter != null) {
                if (tableFilter != null) {
                    List<RowFilter<TableModel, Object>> combinedFilters = new ArrayList<>();
                    combinedFilters.add(tableFilter);
                    combinedFilters.add(columnFilter);

                    if ("AND".equals(disjunctionSelector.getSelectedItem().toString())) {
                        tableFilter = RowFilter.andFilter(combinedFilters);
                    } else {
                        tableFilter = RowFilter.orFilter(combinedFilters);
                    }
                } else {
                    tableFilter = columnFilter;
                }

            }
        }
        RowFilter<TableModel, Object> requestMethodFilter = getRequestMethodFilter();
        if(tableFilter != null) {
            List<RowFilter<TableModel, Object>> filterWithRequestMethod = new ArrayList<>();
            filterWithRequestMethod.add(tableFilter);
            filterWithRequestMethod.add(requestMethodFilter);
            tableSorter.setRowFilter(RowFilter.andFilter(filterWithRequestMethod));
        } else {
            tableSorter.setRowFilter(requestMethodFilter);
        }
    }


    private RowFilter<TableModel, Object>  getRequestMethodFilter() {
        String requestMethod = (String)reqMethodSelector.getSelectedItem();
        int columnIndex = getColumnIndexByColumnName(ApacheLoggingConstants.DESC_REQUEST_METHOD, true, false);
        if(Objects.equals(requestMethod, "ANY")){
            return RowFilter.regexFilter(".*", columnIndex); // Matches everything, removes the filter
        } else {
            return RowFilter.regexFilter(requestMethod, columnIndex);
        }
    }

    private int getColumnIndexByColumnName(String columnName, Boolean modelIndex, Boolean viewIndex) {
        Enumeration<TableColumn> columns = accessLogTbl.getColumnModel().getColumns();

        int index = -1;
        int i = 0;

        while (columns.hasMoreElements()) {
            TableColumn column = columns.nextElement();
            if (columnName.equals(column.getHeaderValue().toString())) {
                index = i;
                break;
            }
            i++;
        }
        index = index >= 0 ? index : 0;

        if(Boolean.TRUE.equals(modelIndex)) {
            index = accessLogTbl.convertColumnIndexToModel(index);
        } else if(Boolean.TRUE.equals(viewIndex)) {
            index = accessLogTbl.convertColumnIndexToView(index);
        }
        return index;
    }

    private void customizeAccessLogTable() {
        JTableHeader header = accessLogTbl.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        AccessLogTableMouseAdapter mouseAdapter = new AccessLogTableMouseAdapter(accessLogTbl);
        accessLogTbl.addMouseListener(mouseAdapter);
        accessLogTbl.addMouseMotionListener(mouseAdapter);
    }

    private void setAccessLogTableCellRenderer(String apacheColumnVal, Class<?> logDataType, int cellIndex) {
        AccessLogTableCellRenderer tableCellRenderer = null;

        if(Objects.equals(apacheColumnVal, ApacheLoggingConstants.FIRST_REQUEST_LINE)) {
            tableCellRenderer = new AccessLogTableWrappedCellRenderer();
        } else {
            tableCellRenderer = new AccessLogTableNormalCellRenderer();
            if (Objects.equals(apacheColumnVal, ApacheLoggingConstants.DATE_TIME) ||
                    Objects.equals(apacheColumnVal, ApacheLoggingConstants.REQUEST_START_TIME)) {

                String dateFormat = Objects.equals(apacheColumnVal, ApacheLoggingConstants.DATE_TIME) ?
                        PropManager.getEndTimeDateFormat() :
                        Objects.equals(apacheColumnVal, ApacheLoggingConstants.REQUEST_START_TIME) ?
                                PropManager.getStartTimeDateFormat() :
                                Constants.DATE_FORMATS.dd_MMM_YYYY.getValue();

                tableCellRenderer.setDateFormat(dateFormat);
            }
        }
        tableCellRenderer.setClassType(logDataType);
        accessLogTbl.getColumnModel().getColumn(cellIndex).setCellRenderer(tableCellRenderer);
    }

    private javax.swing.JTable accessLogTbl;
    private javax.swing.JButton clearFilterBtn;
    private javax.swing.JComboBox<String> columnSelector;
    private javax.swing.JComboBox<String> criteriaSelector;
    private javax.swing.JComboBox<String> disjunctionSelector;
    private javax.swing.JTextField fileNameField;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField patternField;
    private javax.swing.JButton pickFileBtn;
    private javax.swing.JButton processFileBtn;
    private javax.swing.JLabel reqMethodLbl;
    private javax.swing.JComboBox<String> reqMethodSelector;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchField;
}
