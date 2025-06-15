package org.tomcatlogwatcher.core;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.*;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.type.HorizontalTextAlignEnum;
import net.sf.jasperreports.engine.type.StretchTypeEnum;
import net.sf.jasperreports.export.*;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import org.tomcatlogwatcher.dto.ActionDTO;
import org.tomcatlogwatcher.userinterface.models.LogEntryTableModel;
import org.tomcatlogwatcher.utility.AppLogger;

import javax.swing.table.TableModel;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class DataExporter {

    public static final String PDF = "PDF";
    public static final String XLS = "XLSX";

    private final int defaultColumnWidth = 300;

    private TableModel tableModel;
    private int columnCount = 0;

    private JasperDesign jasperDesign;
    private JRDesignBand columnHeader;
    private JRDesignBand detailBand;

    private final static SimpleXlsxReportConfiguration EXCEL_CONFIG = new SimpleXlsxReportConfiguration();

    private final String reportFileOutput;

    private final String FORMAT;

    private List<Class<?>> columnTypes;

    static {
        setExcelPrintConfig();
    }

    public DataExporter(TableModel tableModel, String outputFormat) throws Exception {
        FORMAT = outputFormat;
        if(PDF.equals(outputFormat)) {
            reportFileOutput =  PropManager.getDefaultPdfOutputFile();
        } else {
            reportFileOutput =  PropManager.getDefaultExcelOutputFile();
        }
        initTableModel(tableModel);
        initDesign();
    }

    private static void setExcelPrintConfig(){
        EXCEL_CONFIG.setOnePagePerSheet(false);
        EXCEL_CONFIG.setWrapText(true);
        EXCEL_CONFIG.setDetectCellType(true);
        EXCEL_CONFIG.setCollapseRowSpan(false);
        EXCEL_CONFIG.setIgnorePageMargins(true);
    }

    private void initTableModel(TableModel tableModel) {
        this.tableModel = tableModel;
        this.columnCount = tableModel.getColumnCount();
        this.columnTypes = ((LogEntryTableModel) tableModel).getColumnTypes();
    }

    private void initDesign() throws Exception{
        jasperDesign= new JasperDesign();
        jasperDesign.setName("Exported Data");
        jasperDesign.setPageWidth(595);
        jasperDesign.setPageHeight(842);
        jasperDesign.setLeftMargin(20);
        jasperDesign.setRightMargin(20);
        jasperDesign.setTopMargin(20);
        jasperDesign.setBottomMargin(20);
        if(FORMAT.equals(DataExporter.XLS)) {
            jasperDesign.setIgnorePagination(true);
        }
        setHeaderHeader();
        setDetailBand();
    }


    private void setHeaderHeader() throws Exception {
        columnHeader = new JRDesignBand();
        columnHeader.setHeight(20);

        int x = 0;
        for (int i = 0; i < columnCount; i++) {
            String colName = tableModel.getColumnName(i);

            JRDesignField field = new JRDesignField();
            field.setName(colName);
            field.setValueClass(columnTypes.get(i));
            jasperDesign.addField(field);

            JRDesignStaticText header = new JRDesignStaticText();
            header.setX(x);
            header.setY(0);
            header.setWidth(defaultColumnWidth);
            header.setHeight(20);
            header.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
            header.setText(colName);
            header.setFontSize(12f);
            header.setBold(true);
            columnHeader.addElement(header);

            x += defaultColumnWidth;
        }
    }

    private void setDetailBand() {
        detailBand = new JRDesignBand();
        detailBand.setHeight(20);

        int x = 0;
        for (int i = 0; i < columnCount; i++) {
            String colName = tableModel.getColumnName(i);

            JRDesignTextField textField = new JRDesignTextField();
            textField.setX(x);
            textField.setY(0);
            textField.setWidth(defaultColumnWidth);
            textField.setHeight(20);

            textField.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
            textField.setStretchWithOverflow(true);
            textField.setStretchType(StretchTypeEnum.RELATIVE_TO_TALLEST_OBJECT);

            String defaultDateFormat = PropManager.getDefaultDateFormat();
            Class<?> colClass = columnTypes.get(i);

            String expression = String.join("", "$F{", colName, "}");

            if (colClass == Date.class) {
                expression = String.join("", "new java.text.SimpleDateFormat(\"", defaultDateFormat, "\").format($F{", colName, "})");
            }

            textField.setExpression(new JRDesignExpression(expression));

            textField.setFontSize(10f);
            detailBand.addElement(textField);

            x += defaultColumnWidth;
        }
    }

    public ActionDTO exportData() {
        ActionDTO dto = new ActionDTO();
        dto.setIsSuccessful(false);
        try {
            boolean fileCreated = createExportFile();
            if(fileCreated) {

                JRDesignSection detailSection = (JRDesignSection) jasperDesign.getDetailSection();
                detailSection.addBand(detailBand);
                jasperDesign.setColumnHeader(columnHeader);

                JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
                JRDataSource dataSource = new JRTableModelDataSource(tableModel);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), dataSource);

                if(FORMAT.equals(PDF)) {
                    printToPdf(jasperPrint);
                } else {
                    printToExcel(jasperPrint);
                }
            } else {
                throw new Exception("Export File "+ reportFileOutput +" not be created");
            }
            dto.setIsSuccessful(true);
            dto.setMessage("Successfully exported data to "+reportFileOutput);
        } catch (Exception e) {
            AppLogger.logSevere("Exception in DataExporter.printReport() ",e);
            dto.setIsSuccessful(false);
            dto.setMessage(e.getMessage());
        }
        return dto;
    }

    private boolean createExportFile() throws Exception {
        boolean created = false;
        File outFile = new File(reportFileOutput);
        File parentDir = outFile.getParentFile();

        if(parentDir != null) {
            if(!parentDir.exists()) {
                created = parentDir.mkdirs();
            } else {
                created = true;
            }
            if (created && !outFile.exists()) {
                created = outFile.createNewFile();
            } else {
                created = true;
            }
        }
        return created;
    }

    private void printToExcel(JasperPrint jasperPrint) throws Exception {
        JRXlsxExporter exporter = new JRXlsxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(reportFileOutput));
        exporter.setConfiguration(EXCEL_CONFIG);
        exporter.exportReport();
    }

    private void printToPdf(JasperPrint jasperPrint) throws Exception {
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(reportFileOutput));

        SimplePdfReportConfiguration config = new SimplePdfReportConfiguration();
        config.setSizePageToContent(true);
        config.setForceLineBreakPolicy(false);
        exporter.setConfiguration(config);

        exporter.exportReport();
    }
}
