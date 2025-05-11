package org.tomcatlogwatcher.userinterface;

import org.tomcatlogwatcher.core.AccessLogDbOperationService;
import org.tomcatlogwatcher.core.DataExporter;
import org.tomcatlogwatcher.data.AccessLogInfoService;
import org.tomcatlogwatcher.data.ApacheLoggingConstants;
import org.tomcatlogwatcher.data.Constants;
import org.tomcatlogwatcher.core.AccessLogFileOperationService;
import org.tomcatlogwatcher.core.PropManager;
import org.tomcatlogwatcher.dto.AccessLogInfoDTO;
import org.tomcatlogwatcher.dto.ActionDTO;
import org.tomcatlogwatcher.userinterface.renderers.AccessLogTableCellRenderer;
import org.tomcatlogwatcher.userinterface.renderers.AccessLogTableNormalCellRenderer;
import org.tomcatlogwatcher.userinterface.adapters.AccessLogTableMouseAdapter;
import org.tomcatlogwatcher.userinterface.renderers.AccessLogTableWrappedCellRenderer;
import org.tomcatlogwatcher.utility.AppLogger;
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

    private final List<String> queryHistory = new ArrayList<>();
    private int queryPointer = -1;


    public AccessLogViewer() {
        initComponents();
    }

    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem2 = new javax.swing.JCheckBoxMenuItem();
        fileNameField = new javax.swing.JTextField();
        patternField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        pickFileBtn = new javax.swing.JButton();
        processFileBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        accessLogTbl = new javax.swing.JTable();
        clearFilterBtn = new javax.swing.JButton();
        searchButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        sqlText = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        outputBox = new javax.swing.JTextPane();
        prevQueryBtn = new javax.swing.JButton();
        nextQueryBtn = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        columnNameLbl = new javax.swing.JTextPane();
        exportDataBt = new javax.swing.JButton();
        formatSelector = new javax.swing.JComboBox<>();

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        jCheckBoxMenuItem2.setSelected(true);
        jCheckBoxMenuItem2.setText("jCheckBoxMenuItem2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        fileNameField.setFont(new java.awt.Font("Arial", Font.PLAIN, 14)); // NOI18N
        fileNameField.setEnabled(false);
        fileNameField.setMaximumSize(new java.awt.Dimension(480, 25));
        fileNameField.setMinimumSize(new java.awt.Dimension(480, 25));
        fileNameField.setName(""); // NOI18N
        fileNameField.setPreferredSize(new java.awt.Dimension(480, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.ipadx = 19;
        gridBagConstraints.ipady = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 12, 0, 0);
        getContentPane().add(fileNameField, gridBagConstraints);

        patternField.setFont(new java.awt.Font("Arial", Font.PLAIN, 14)); // NOI18N
        patternField.setMaximumSize(new java.awt.Dimension(480, 25));
        patternField.setMinimumSize(new java.awt.Dimension(480, 25));
        patternField.setPreferredSize(new java.awt.Dimension(480, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = 19;
        gridBagConstraints.ipady = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(14, 12, 0, 0);
        getContentPane().add(patternField, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Arial", Font.BOLD, 14)); // NOI18N
        jLabel2.setText("Pattern");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(21, 6, 0, 0);
        getContentPane().add(jLabel2, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Arial", Font.BOLD, 14)); // NOI18N
        jLabel3.setText("File");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 16;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(25, 6, 0, 0);
        getContentPane().add(jLabel3, gridBagConstraints);

        pickFileBtn.setFont(new java.awt.Font("Arial", Font.BOLD, 14)); // NOI18N
        pickFileBtn.setText("PICK FILE");
        pickFileBtn.setMaximumSize(new java.awt.Dimension(145, 25));
        pickFileBtn.setMinimumSize(new java.awt.Dimension(145, 25));
        pickFileBtn.setPreferredSize(new java.awt.Dimension(145, 25));
        pickFileBtn.addActionListener(this::pickFileBtnActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = 25;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(21, 18, 0, 0);
        getContentPane().add(pickFileBtn, gridBagConstraints);

        processFileBtn.setFont(new java.awt.Font("Arial", Font.BOLD, 14));
        processFileBtn.setText("PROCESS FILE");
        processFileBtn.setEnabled(false);
        processFileBtn.setMaximumSize(new java.awt.Dimension(145, 25));
        processFileBtn.setMinimumSize(new java.awt.Dimension(145, 25));
        processFileBtn.setPreferredSize(new java.awt.Dimension(145, 25));
        processFileBtn.addActionListener(this::processFileBtnActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 25;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(17, 18, 0, 0);
        getContentPane().add(processFileBtn, gridBagConstraints);

        accessLogTbl.setFont(new java.awt.Font("Arial", Font.PLAIN, 18));
        accessLogTbl.setRowHeight(50);
        jScrollPane1.setViewportView(accessLogTbl);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.gridwidth = 17;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1177;
        gridBagConstraints.ipady = 386;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 10);
        getContentPane().add(jScrollPane1, gridBagConstraints);

        clearFilterBtn.setFont(new java.awt.Font("Arial", Font.BOLD, 14));
        clearFilterBtn.setText("CLEAR FILTER");
        clearFilterBtn.setEnabled(false);
        clearFilterBtn.setMaximumSize(new java.awt.Dimension(130, 25));
        clearFilterBtn.setMinimumSize(new java.awt.Dimension(130, 25));
        clearFilterBtn.setPreferredSize(new java.awt.Dimension(130, 25));
        customizeAccessLogTable();
        clearFilterBtn.addActionListener(this::clearFilterBtnActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 18, 0, 0);
        getContentPane().add(clearFilterBtn, gridBagConstraints);

        searchButton.setFont(new java.awt.Font("Arial", Font.BOLD, 14));
        searchButton.setText("EXECUTE");
        searchButton.setEnabled(false);
        searchButton.setMaximumSize(new java.awt.Dimension(130, 25));
        searchButton.setMinimumSize(new java.awt.Dimension(130, 25));
        searchButton.setPreferredSize(new java.awt.Dimension(130, 25));
        searchButton.addActionListener(this::searchButtonActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 18, 0, 0);
        getContentPane().add(searchButton, gridBagConstraints);

        sqlText.setColumns(20);
        sqlText.setFont(new java.awt.Font("Lucida Console", Font.PLAIN, 18));
        sqlText.setRows(5);
        jScrollPane2.setViewportView(sqlText);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridheight = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 486;
        gridBagConstraints.ipady = 89;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(12, 9, 0, 0);
        getContentPane().add(jScrollPane2, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Arial", Font.BOLD, 14));
        jLabel7.setText("SQL");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 6, 0, 0);
        getContentPane().add(jLabel7, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Arial", Font.BOLD, 14));
        jLabel1.setText("TABLE NAME: "+PropManager.getLogTableName());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 84;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(17, 18, 0, 0);
        getContentPane().add(jLabel1, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Arial", Font.BOLD, 14));
        jLabel4.setText("OUTPUT");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.ipadx = 17;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(17, 6, 0, 0);
        getContentPane().add(jLabel4, gridBagConstraints);

        outputBox.setEditable(false);
        outputBox.setContentType("text/html"); // NOI18N
        outputBox.setText("");
        outputBox.setToolTipText("");
        jScrollPane5.setViewportView(outputBox);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 10;
        gridBagConstraints.gridheight = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 218;
        gridBagConstraints.ipady = 123;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 12, 0, 10);
        getContentPane().add(jScrollPane5, gridBagConstraints);

        prevQueryBtn.setFont(new java.awt.Font("Arial", Font.BOLD, 14));
        prevQueryBtn.setText("<<");
        prevQueryBtn.addActionListener(this::prevQueryBtnActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 18, 0, 0);
        getContentPane().add(prevQueryBtn, gridBagConstraints);

        nextQueryBtn.setFont(new java.awt.Font("Arial", Font.BOLD, 14));
        nextQueryBtn.setText(">>");
        nextQueryBtn.addActionListener(this::nextQueryBtnActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 0);
        getContentPane().add(nextQueryBtn, gridBagConstraints);

        columnNameLbl.setBackground(javax.swing.UIManager.getDefaults().getColor("Panel.background"));
        columnNameLbl.setContentType("text/html");
        columnNameLbl.setFont(new java.awt.Font("Arial", Font.BOLD, 14));
        columnNameLbl.setText("");
        jScrollPane4.setViewportView(columnNameLbl);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 17;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 164;
        gridBagConstraints.ipady = 155;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 18, 0, 0);
        getContentPane().add(jScrollPane4, gridBagConstraints);

        exportDataBt.setFont(new java.awt.Font("Arial", Font.BOLD, 14));
        exportDataBt.setText("EXPORT DATA");
        exportDataBt.setEnabled(false);
        exportDataBt.setMaximumSize(new java.awt.Dimension(130, 25));
        exportDataBt.setMinimumSize(new java.awt.Dimension(130, 25));
        exportDataBt.setPreferredSize(new java.awt.Dimension(130, 25));
        exportDataBt.addActionListener(this::exportDataBtActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 12;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(7, 12, 0, 10);
        getContentPane().add(exportDataBt, gridBagConstraints);

        formatSelector.setFont(new java.awt.Font("Arial", Font.BOLD, 14));
        formatSelector.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { DataExporter.PDF, DataExporter.XLS}));
        formatSelector.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 12, 0, 0);
        getContentPane().add(formatSelector, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pickFileBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pickFileBtnActionPerformed
        String newFile = openFileDialog(this);
        fileNameField.setText(newFile);
        processFileBtn.setEnabled(Utils.areStringsValid(newFile));
    }//GEN-LAST:event_pickFileBtnActionPerformed

    private void processFileBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_processFileBtnActionPerformed
        String pattern = Utils.areStringsValid(patternField.getText()) ? patternField.getText() : PropManager.getDefaultPattern();
        UIUtils.setOutputText("Processing.... ", true, this.outputBox);
        enableDisableButtons(false);
        Runnable r = () -> {
            try {
                ActionDTO actionDTO = AccessLogFileOperationService.loadAccessLogFile(fileNameField.getText(), pattern);
                if (actionDTO.getIsSuccessful()) {
                    setDataInTable((LogEntryTableModel) actionDTO.getData());
                }
                UIUtils.setOutputText(actionDTO.getMessage(), actionDTO.getIsSuccessful(), this.outputBox);
                enableDisableButtons(actionDTO.getIsSuccessful());
            } catch (Exception e) {
                enableDisableButtons(false);
                processFileBtn.setEnabled(true);
                UIUtils.setOutputText(e.getMessage(), false, this.outputBox);
                AppLogger.logSevere("Error in AccessLogViewer.processFileBtnActionPerformed()", e);
            } finally {
                System.gc();
            }
        };

        Thread th = new Thread(r);
        th.start();

    }//GEN-LAST:event_processFileBtnActionPerformed

    private void clearFilterBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearFilterBtnActionPerformed
        filterTable(Constants.ALL_SELECTION_QUERY);
    }//GEN-LAST:event_clearFilterBtnActionPerformed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        String sql = this.sqlText.getText();
        filterTable(sql);
    }//GEN-LAST:event_searchButtonActionPerformed

    private void prevQueryBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prevQueryBtnActionPerformed
        if(queryPointer>0){
            queryPointer--;
            this.sqlText.setText(queryHistory.get(queryPointer));
        }
    }//GEN-LAST:event_prevQueryBtnActionPerformed

    private void nextQueryBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextQueryBtnActionPerformed
        if(queryPointer < queryHistory.size() - 1) {
            queryPointer++;
            this.sqlText.setText(queryHistory.get(queryPointer));
        }
    }//GEN-LAST:event_nextQueryBtnActionPerformed

    private void exportDataBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportDataBtActionPerformed
        UIUtils.setOutputText("Exporting Data... ", true, this.outputBox);
        enableDisableButtons(false);
        Runnable r = () -> {
            try {
                String format = formatSelector.getSelectedItem().toString();
                format = format == null ? DataExporter.XLS : format;
                DataExporter dataExporter = new DataExporter(accessLogTbl.getModel(), format);
                ActionDTO actionDTO = dataExporter.exportData();
                UIUtils.setOutputText(actionDTO.getMessage(), actionDTO.getIsSuccessful(), this.outputBox);
            } catch (Exception e) {
                AppLogger.logSevere("Error AccessLogViewScreen.exportData", e);
            } finally {
                enableDisableButtons(true);
                System.gc();
            }
        };
        Thread th = new Thread(r);
        th.start();
    }//GEN-LAST:event_exportDataBtActionPerformed


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

    private void filterTable(String sql) {
        try {
            if (UIUtils.validateDQL(sql)) {
                this.queryHistory.add(sql);
                this.queryPointer++;
                ActionDTO actionDTO = AccessLogDbOperationService.getFilteredAccessLogEntries(sql);
                if (actionDTO.getIsSuccessful()) {
                    setDataInTable((LogEntryTableModel) actionDTO.getData());
                }
                UIUtils.setOutputText(actionDTO.getMessage(), actionDTO.getIsSuccessful(), this.outputBox);
            } else {
                JOptionPane.showMessageDialog(this, "Only query language is supported", "Error Message", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            AppLogger.logSevere("Error AccessLogViewScreen.filterTable()", e);
            UIUtils.setOutputText(e.getMessage(), false, this.outputBox);
        }
    }

    private void setDataInTable(AbstractTableModel tableModel) {
        accessLogTbl.setModel(tableModel);
        accessLogTbl.setRowSorter(new TableRowSorter<>(accessLogTbl.getModel()));

        List<String> dbColumnNames = new ArrayList<>();
        for (int i = 0; i < accessLogTbl.getColumnCount(); i++) {
            String columnName = accessLogTbl.getColumnName(i);
            AccessLogInfoDTO infoDTO = AccessLogInfoService.getAccessLogInfoByDescription(columnName, false);
            dbColumnNames.add(infoDTO!=null ? infoDTO.getDbColumnName().toLowerCase(): columnName.toLowerCase());
            setAccessLogTableCellRenderer(i, infoDTO);
        }
        setDbColumnNames(dbColumnNames);
    }

    private void customizeAccessLogTable() {
        JTableHeader header = accessLogTbl.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        AccessLogTableMouseAdapter mouseAdapter = new AccessLogTableMouseAdapter(accessLogTbl);
        accessLogTbl.addMouseListener(mouseAdapter);
        accessLogTbl.addMouseMotionListener(mouseAdapter);
    }

    private void setAccessLogTableCellRenderer(int cellIndex, AccessLogInfoDTO infoDTO) {
        AccessLogTableCellRenderer tableCellRenderer = null;
        if(infoDTO!=null) {
            if (infoDTO.isLongText()) {
                tableCellRenderer = new AccessLogTableWrappedCellRenderer();
            } else {
                tableCellRenderer = new AccessLogTableNormalCellRenderer();
                String apacheColumnVal = infoDTO.getApachePattern();
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
            tableCellRenderer.setClassType(infoDTO.getJavaType());
        } else {
            tableCellRenderer = new AccessLogTableNormalCellRenderer();
        }

        accessLogTbl.getColumnModel().getColumn(cellIndex).setCellRenderer(tableCellRenderer);
    }

    private void setDbColumnNames(List<String> dbColumnNames) {
        StringBuilder columnLbl = new StringBuilder("<html><b>Column Names</b><br>");
        for(String dbColName : dbColumnNames) {
            columnLbl.append(dbColName).append("<br>");
        }
        columnLbl.append("</html>");
        this.columnNameLbl.setText(columnLbl.toString());
    }

    private void enableDisableButtons(boolean enable) {
        this.processFileBtn.setEnabled(enable);
        this.exportDataBt.setEnabled(enable);
        this.searchButton.setEnabled(enable);
        this.clearFilterBtn.setEnabled(enable);
        this.formatSelector.setEnabled(enable);
    }

    private javax.swing.JTable accessLogTbl;
    private javax.swing.JButton clearFilterBtn;
    private javax.swing.JTextPane columnNameLbl;
    private javax.swing.JButton exportDataBt;
    private javax.swing.JTextField fileNameField;
    private javax.swing.JComboBox<String> formatSelector;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JButton nextQueryBtn;
    private javax.swing.JTextPane outputBox;
    private javax.swing.JTextField patternField;
    private javax.swing.JButton pickFileBtn;
    private javax.swing.JButton prevQueryBtn;
    private javax.swing.JButton processFileBtn;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextArea sqlText;
}
