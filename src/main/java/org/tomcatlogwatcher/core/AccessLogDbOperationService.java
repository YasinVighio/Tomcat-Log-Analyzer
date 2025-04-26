package org.tomcatlogwatcher.core;

import org.tomcatlogwatcher.data.AccessLogInfoService;
import org.tomcatlogwatcher.dataaccess.DBConnector;
import org.tomcatlogwatcher.dto.AccessLogInfoDTO;
import org.tomcatlogwatcher.dto.ActionDTO;
import org.tomcatlogwatcher.dto.LogEntryDTO;
import org.tomcatlogwatcher.utility.AppLogger;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.List;
import java.util.Optional;

public class AccessLogDbOperationService {

    public static void createLogTable(List<String> logEntryApacheColumns) {
        try {
            List<AccessLogInfoDTO> columnInfoDTOs = AccessLogInfoService.getAccessLogInfoDTOsByPatterns(logEntryApacheColumns);

            String tableName = PropManager.getLogTableName();

            String createTableSQL = generateCreateTableSQL(tableName, columnInfoDTOs);

            Connection conn = null;
            PreparedStatement pstmt = null;
            try {
                conn = DBConnector.getConnection();
                pstmt = conn.prepareStatement(createTableSQL);
                pstmt.executeUpdate();
            } catch (Exception e) {
                AppLogger.logSevere("Exception in AccessLogDbOperationService.createLogTable while creating table", e);
                DBConnector.rollback(conn);
            } finally {
                DBConnector.closeDbObject(pstmt, conn);
            }

        } catch (Exception e){
            AppLogger.logSevere("Error in AccessLogDbOperationService.createLogTable()", e);
        }
    }

    public static void insertLogEntries(List<LogEntryDTO> logEntries, List<String> patternParts) {
        int columnCount = patternParts.size();

        StringBuilder placeholdersBuilder = new StringBuilder();
        for (int i = 0; i <= columnCount; i++) {
            placeholdersBuilder.append("?");
            if (i < columnCount) {
                placeholdersBuilder.append(", ");
            }
        }

        String sql = new StringBuilder("INSERT INTO access_log VALUES (").append(placeholdersBuilder).append(")").toString();

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBConnector.getConnection();
            pstmt = conn.prepareStatement(sql);

            int recordId = 1;
            for (LogEntryDTO entry : logEntries) {
                int paramIndex = 1;

                pstmt.setObject(paramIndex++, recordId++);

                for (int i = 0; i < columnCount; i++) {
                    pstmt.setObject(paramIndex++, entry.getValueByApachePlaceholder(patternParts.get(i)));
                }

                pstmt.addBatch();
            }

            pstmt.executeBatch();
            conn.commit();

        } catch (Exception e) {
            AppLogger.logSevere("Exception in AccessLogDbOperationService.insertLogEntries", e);
            DBConnector.rollback(conn);
        } finally {
            DBConnector.closeDbObject(pstmt, conn);
        }
    }


    public static String generateCreateTableSQL(String tableName, List<AccessLogInfoDTO> columnInfoDTOs) {
        StringBuilder sql = new StringBuilder();

        sql.append("DROP TABLE IF EXISTS ").append(tableName).append(";\n");

        sql.append("CREATE TABLE IF NOT EXISTS ").append(tableName).append(" (\n");

        sql.append("    record_id BIGINT PRIMARY KEY,\n");

        int columnCount = 1;
        for(AccessLogInfoDTO columnInfo : columnInfoDTOs) {
            String comma = columnCount < columnInfoDTOs.size() ? "," : "";
            sql.append("    ")
                    .append(columnInfo.getDbColumnName())
                    .append(" ")
                    .append(columnInfo.getSqlType())
                    .append(comma).append("\n");
            columnCount++;
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
        } catch (Exception e) {
            AppLogger.logSevere("Exception in AccessLogDbOperationService.getFilteredAccessLogEntries", e);
            actionDTO.setIsSuccessful(false);
            if(e instanceof SQLException){
                actionDTO.setMessage(e.getMessage());
            } else {
                actionDTO.setMessage("Error occurred while executing query: " + sql);
            }
        } finally {
            DBConnector.closeDbObject(rs, pstmt, conn);
        }
        return actionDTO;
    }

}
