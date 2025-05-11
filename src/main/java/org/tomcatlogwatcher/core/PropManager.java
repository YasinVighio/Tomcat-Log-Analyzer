package org.tomcatlogwatcher.core;

import org.tomcatlogwatcher.data.Constants;
import org.tomcatlogwatcher.utility.AppLogger;

import java.io.FileInputStream;
import java.util.*;

public class PropManager {
    private static final Properties properties = new Properties();

    private static final String DEFAULT_PATTERN_PROP_NAME = "defaultPattern";
    private static final String FILTER_CASE_SENSITIVITY_PROP_NAME = "caseSensitiveColumn";

    private static final String START_TIME_DATE_FORMAT_PROP_NAME = "startTimeDateFormat";
    private static final String END_TIME_DATE_FORMAT_PROP_NAME = "endTimeDateFormat";
    private static final String DEFAULT_DATE_FORMAT_PROP_NAME = "defaultDateFormat";

    private static final String LOG_TABLE_PROP_NAME = "logDbTableName";

    private static final String DEFAULT_EXCEL_FILE_PROP_NAME = "excelReportDefaultFileName";

    private static final String DEFAULT_PDF_FILE_PROP_NAME = "pdfReportDefaultFileName";

    private static final String PROP_FILE_PATH = "configs/app.ini";

    public static void initProperties() throws Exception{
        try (FileInputStream input = new FileInputStream(PROP_FILE_PATH)) {
            properties.load(input);
        } catch (Exception e) {
            AppLogger.logSevere("Error in PropManager.initProperties", e);
            throw e;
        }
    }

    public static String getDefaultPattern() {
        String defaultPattern = Constants.DEFAULT_PATTERN;
        try {
            defaultPattern = properties.getProperty(DEFAULT_PATTERN_PROP_NAME);
        } catch (Exception e){
            AppLogger.logSevere("Error in PropManager.getDefaultPattern", e);
        }
        return defaultPattern;
    }

    public static String getStartTimeDateFormat() {
        String defaultPattern = Constants.DATE_FORMATS.COMMON_LOGGING_FORMAT_WITH_MS_WITHOUT_ZONE.getValue();
        try {
            defaultPattern = properties.getProperty(START_TIME_DATE_FORMAT_PROP_NAME);
        } catch (Exception e){
            AppLogger.logSevere("Error in PropManager.getStartTimeDateFormat", e);
        }
        return defaultPattern;
    }

    public static String getEndTimeDateFormat() {
        String defaultPattern = Constants.DATE_FORMATS.COMMON_LOGGING_FORMAT_WITHOUT_ZONE.getValue();
        try {
            defaultPattern = properties.getProperty(END_TIME_DATE_FORMAT_PROP_NAME);
        } catch (Exception e){
            AppLogger.logSevere("Error in PropManager.getEndTimeDateFormat", e);
        }
        return defaultPattern;
    }

    public static String getDefaultDateFormat() {
        String defaultPattern = Constants.DATE_FORMATS.dd_MM_YYYY_HH_mm_ss.getValue();
        try {
            defaultPattern = properties.getProperty(DEFAULT_DATE_FORMAT_PROP_NAME);
        } catch (Exception e){
            AppLogger.logSevere("Error in PropManager.getDefaultDateFormat", e);
        }
        return defaultPattern;
    }

    public static boolean getCaseSensitiveColumn() {
        boolean value = false;
        try {
            String prop = Optional.ofNullable(properties.getProperty(FILTER_CASE_SENSITIVITY_PROP_NAME))
                    .orElse("false");
            value = Boolean.parseBoolean(prop);
        } catch (Exception e){
            AppLogger.logSevere("Error in PropManager.getCaseSensitiveColumn", e);
        }
        return value;
    }

    public static String getLogTableName() {
        String tableName = Constants.DEFAULT_LOG_TABLE_NAME;
        try {
            tableName = properties.getProperty(LOG_TABLE_PROP_NAME);
        } catch (Exception e){
            AppLogger.logSevere("Error in PropManager.getLogTableName", e);
        }
        return tableName;
    }

    public static String getDefaultExcelOutputFile() {
        String tableName = Constants.EXCEL_REPORT_DEFAULT_FILE_NAME;
        try {
            tableName = properties.getProperty(DEFAULT_EXCEL_FILE_PROP_NAME);
        } catch (Exception e){
            AppLogger.logSevere("Error in PropManager.getDefaultExcelOutputFile", e);
        }
        return tableName;
    }


    public static String getDefaultPdfOutputFile() {
        String tableName = Constants.PDF_REPORT_DEFAULT_FILE_NAME;
        try {
            tableName = properties.getProperty(DEFAULT_PDF_FILE_PROP_NAME);
        } catch (Exception e){
            AppLogger.logSevere("Error in PropManager.getDefaultPdfOutputFile", e);
        }
        return tableName;
    }
}
