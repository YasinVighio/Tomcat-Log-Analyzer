package org.tomcatlogwatcher.core;

import org.tomcatlogwatcher.data.ApacheLoggingConstants;
import org.tomcatlogwatcher.data.AccessLogInfoService;
import org.tomcatlogwatcher.dataaccess.DBConnector;
import org.tomcatlogwatcher.dataaccess.DbUtil;
import org.tomcatlogwatcher.dto.AccessLogDTO;
import org.tomcatlogwatcher.dto.AccessLogInfoDTO;
import org.tomcatlogwatcher.dto.ActionDTO;
import org.tomcatlogwatcher.dto.LogEntryDTO;
import org.tomcatlogwatcher.utility.AppLogger;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AccessLogDbOperationService {
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

            Connection conn = null;
            PreparedStatement pstmt = null;
            try {
                conn = DBConnector.getConnection();
                pstmt = conn.prepareStatement(createTableSQL);
                pstmt.executeUpdate();
            } catch (Exception e) {
                AppLogger.logSevere("Exception in AccessLogOperations.createLogTable while creating table", e);
                DBConnector.rollback(conn);
            } finally {
                DBConnector.closeDbObject(pstmt, conn);
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
            DBConnector.closeDbObject(pstmt, conn);
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
            String sqlType = DbUtil.mapJavaTypeToH2Type(colType);

            sql.append("    ").append(colName).append(" ").append(sqlType);
            if (i < columnNames.size() - 1) {
                sql.append(",");
            }
            sql.append("\n");
        }

        sql.append(");");
        return sql.toString();
    }

    public static ActionDTO getFilteredAccessLogEntries(String sql) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ActionDTO actionDTO = new ActionDTO();
        actionDTO.setIsSuccessful(false);
        DefaultTableModel tableModel = null;
        try {
            conn = DBConnector.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            String[] columnNames = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columnNames[i - 1] = Optional.ofNullable(AccessLogInfoService.getAccessLogInfoByDbColumn(metaData.getColumnName(i), false))
                        .map(AccessLogInfoDTO::getDescription)
                        .orElse(metaData.getColumnName(i));
            }

            tableModel = new DefaultTableModel(columnNames, 0);

            while (rs.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = rs.getObject(i);
                }
                tableModel.addRow(row);
            }
            actionDTO.setIsSuccessful(true);
            actionDTO.setData(tableModel);
        } catch (SQLException e) {
            AppLogger.logSevere("Exception in AccessLogOperations.getFilteredAccessLogEntries", e);
            actionDTO.setIsSuccessful(false);
            actionDTO.setMessage(e.getMessage());
        } catch (Exception e) {
            AppLogger.logSevere("Exception in AccessLogOperations.getFilteredAccessLogEntries", e);
            actionDTO.setIsSuccessful(false);
            actionDTO.setMessage("Error occurred while executing query: " + sql);
        } finally {
            DBConnector.closeDbObject(rs, pstmt, conn);
        }
        return actionDTO;
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
