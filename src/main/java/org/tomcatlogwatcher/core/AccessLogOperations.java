package org.tomcatlogwatcher.core;

import org.tomcatlogwatcher.data.ApacheLoggingConstants;
import org.tomcatlogwatcher.dataaccess.DBConnector;
import org.tomcatlogwatcher.dto.AccessLogDTO;
import org.tomcatlogwatcher.dto.ActionDTO;
import org.tomcatlogwatcher.dto.LogEntryDTO;
import org.tomcatlogwatcher.utility.AppLogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.List;
import java.util.stream.Collectors;

public class AccessLogOperations {
    public static ActionDTO saveLogsInDb(List<LogEntryDTO> logEntries) {
        return null;
    }

    public static void createLogTable(AccessLogDTO accessLogDTO) {
        try {

            List<String> tableColumns = accessLogDTO.getColumnApacheValues().stream()
                    .map(ApacheLoggingConstants.LOG_DB_COL_MAP::get).collect(Collectors.toList());

            List<Class> tableColumnTypes =  accessLogDTO.getColumnApacheValues().stream()
                    .map(ApacheLoggingConstants.LOG_DATA_TYPE_MAP::get).collect(Collectors.toList());

            String createTableSQL = generateCreateTableSQL("access_log", tableColumns, tableColumnTypes);

            Connection connection = null;
            try {
                connection = DBConnector.getConnection();
                connection.prepareStatement(createTableSQL).executeUpdate();
            } catch (Exception e) {
                AppLogger.logSevere("Exception in AccessLogOperations.createLogTable while creating table", e);
                DBConnector.rollback(connection);
            } finally {
                DBConnector.closeConnection(connection);
            }

        } catch (Exception e){
            AppLogger.logSevere("Error in AccessLogOperations.createLogTable()", e);
        }
    }

    public static void insertLogEntries(List<LogEntryDTO> logEntries, AccessLogDTO accessLogDTO) {
        List<String> apacheColumns = accessLogDTO.getColumnApacheValues();
        int columnCount = apacheColumns.size();

        StringBuilder placeholdersBuilder = new StringBuilder();
        for (int i = 0; i < columnCount; i++) {
            placeholdersBuilder.append("?");
            if (i < columnCount - 1) {
                placeholdersBuilder.append(", ");
            }
        }

        String sql = "INSERT INTO access_log VALUES (" + placeholdersBuilder.toString() + ")";

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBConnector.getConnection();
            pstmt = conn.prepareStatement(sql);

            for (LogEntryDTO entry : logEntries) {
                for (int i = 0; i < columnCount; i++) {
                    pstmt.setObject(i + 1, entry.getValueByApachePlaceholder(accessLogDTO.getColumnApacheValues().get(i)));
                }

                pstmt.addBatch();
            }

            pstmt.executeBatch();
            conn.commit();

        } catch (Exception e) {
            AppLogger.logSevere("Exception in AccessLogOperations.insertLogEntries", e);
            DBConnector.rollback(conn);
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                DBConnector.closeConnection(conn);
            } catch (Exception e) {
                AppLogger.logSevere("Exception in AccessLogOperations.insertLogEntries", e);
            }
        }
    }


    public static String generateCreateTableSQL(String tableName, List<String> columnNames, List<Class> columnTypes) {
        if (columnNames.size() != columnTypes.size()) {
            throw new IllegalArgumentException("Column names and types must be the same length.");
        }

        StringBuilder sql = new StringBuilder();

        sql.append("DROP TABLE IF EXISTS ").append(tableName).append(";\n");

        sql.append("CREATE TABLE IF NOT EXISTS ").append(tableName).append(" (\n");


        for (int i = 0; i < columnNames.size(); i++) {
            String colName = columnNames.get(i);
            Class<?> colType = columnTypes.get(i);
            String sqlType = mapJavaTypeToH2Type(colType);

            sql.append("    ").append(colName).append(" ").append(sqlType);
            if (i < columnNames.size() - 1) {
                sql.append(",");
            }
            sql.append("\n");
        }

        sql.append(");");
        return sql.toString();
    }

    private static String mapJavaTypeToH2Type(Class<?> javaType) {
        if (javaType == String.class) return "VARCHAR";
        if (javaType == Integer.class || javaType == int.class) return "INT";
        if (javaType == Long.class || javaType == long.class) return "BIGINT";
        if (javaType == Double.class || javaType == double.class) return "DOUBLE";
        if (javaType == Float.class || javaType == float.class) return "REAL";
        if (javaType == Boolean.class || javaType == boolean.class) return "BOOLEAN";
        if (javaType == java.sql.Timestamp.class || javaType == java.util.Date.class) return "TIMESTAMP";
        return "VARCHAR";
    }

    public static void test() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM access_log where request like '%manageFolioPayment%'";

        try {
            conn = DBConnector.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                System.out.println("---- Access Log Entry ----");
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object value = rs.getObject(i);
                    System.out.println(columnName + ": " + value);
                }
            }

        } catch (Exception e) {
            AppLogger.logSevere("Exception in AccessLogOperations.test", e);
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null) pstmt.close();
                DBConnector.closeConnection(conn);
            } catch (Exception e) {
                AppLogger.logSevere("Exception in AccessLogOperations.test", e);
            }
        }
    }


}
