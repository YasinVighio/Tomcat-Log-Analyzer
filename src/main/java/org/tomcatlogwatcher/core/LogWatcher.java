package org.tomcatlogwatcher.core;

import org.tomcatlogwatcher.data.ApacheLoggingConstants;
import org.tomcatlogwatcher.data.Constants;
import org.tomcatlogwatcher.dto.AccessLogDTO;
import org.tomcatlogwatcher.dto.ActionDTO;
import org.tomcatlogwatcher.dto.LogEntryDTO;
import org.tomcatlogwatcher.utility.AppLogger;
import org.tomcatlogwatcher.utility.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class LogWatcher {

    private static final String COLUMN_APACHE_VALUES_IDENTIFIER = "COLUMN_APACHE_VALUES";
    private static final String TABLE_HEADER_VALUES_IDENTIFIER = "TABLE_HEADER_VALUES";

    public static ActionDTO readAccessFile(String accessFile, String pattern) {
        ActionDTO actionDTO = new ActionDTO();
        actionDTO.setIsSuccessful(false);
        try {
            AccessLogDTO accessLogDTO = new AccessLogDTO();

            List<String> patternParts = readPattern(pattern);
            Map<String, List<String>> tableColumnData = getTableColumnData(patternParts);

            accessLogDTO.setColumnApacheValues(tableColumnData.get(COLUMN_APACHE_VALUES_IDENTIFIER));
            accessLogDTO.setHeaders(tableColumnData.get(TABLE_HEADER_VALUES_IDENTIFIER));

            List<LogEntryDTO> logEntries = readLogEntriesFromFile(accessFile, patternParts);

            accessLogDTO.setRequestMethods(getUniqueRequestMethodsFromLogEntries(logEntries));

            accessLogDTO.setLogEntries(logEntries);

            accessLogDTO.setAccessLogDate(getAccessLogDateFromLogEntryDTO(logEntries.get(0)));

            actionDTO.setData(accessLogDTO);
            actionDTO.setIsSuccessful(true);

            AccessLogOperations.createLogTable(accessLogDTO);

            AccessLogOperations.insertLogEntries(logEntries, accessLogDTO);

            //AccessLogOperations.test();

        } catch (Exception e) {
            AppLogger.logSevere("Exception in LogWatcher.readAccessFile", e);
            actionDTO.setMessage("An error occurred while processing file");
        }
        return actionDTO;
    }

    private static List<LogEntryDTO> readLogEntriesFromFile(String accessFile, List<String> patternParts) throws Exception {
        List<LogEntryDTO> logEntries = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(accessFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                logEntries.add(getLogEntryFromLogString(line, patternParts));
            }
        } catch (Exception e) {
            AppLogger.logSevere("Exception in LogWatcher.readAccessLogs", e);
            throw e;
        }
        return logEntries;
    }

    private static Map<String, List<String>> getTableColumnData(List<String> patternParts) {
        Map<String, List<String>> tableColumnData = new HashMap<>();
        try {
            List<String> columnApacheValues = new ArrayList<>();
            List<String> tableHeaderValues = new ArrayList<>();
            for(String patternPart : patternParts) {
                if(ApacheLoggingConstants.LOG_FORMAT_MAP.containsKey(patternPart)){
                    if(patternPart.equals(ApacheLoggingConstants.DATE_TIME)){
                        tableHeaderValues.add(ApacheLoggingConstants.DESC_REQUEST_START_TIME);
                        columnApacheValues.add(ApacheLoggingConstants.REQUEST_START_TIME);
                    }
                    if(patternPart.equals(ApacheLoggingConstants.FIRST_REQUEST_LINE)){
                        tableHeaderValues.add(ApacheLoggingConstants.DESC_REQUEST_METHOD);
                        columnApacheValues.add(ApacheLoggingConstants.REQUEST_METHOD);
                    }
                    tableHeaderValues.add(ApacheLoggingConstants.LOG_FORMAT_MAP.get(patternPart));
                    columnApacheValues.add(patternPart);
                }
            }
            tableColumnData.put(COLUMN_APACHE_VALUES_IDENTIFIER, columnApacheValues);
            tableColumnData.put(TABLE_HEADER_VALUES_IDENTIFIER, tableHeaderValues);
        } catch (Exception e){
            AppLogger.logSevere("Exception in LogWatcher.getTableColumnData", e);
        }
        return tableColumnData;
    }

    private static List<String> readPattern(String pattern) {
        List<String> patternParts;
        try {
            pattern = pattern.trim();
            patternParts = Arrays.asList(pattern.split(" "));
            patternParts.replaceAll(string -> string.replace("\"", "").trim());
        } catch (Exception e){
            AppLogger.logSevere("Exception in LogWatcher.readPattern", e);
            throw e;
        }
        return patternParts;
    }

    private static LogEntryDTO getLogEntryFromLogString(String logEntryString, List<String> patternParts) {
        LogEntryDTO logEntry = new LogEntryDTO();
        try{
            List<String> logParts = Arrays.asList(logEntryString.trim().split(" "));

            int logPartCounter = 0;

            for(String patternPart : patternParts){
                if(ApacheLoggingConstants.LOG_FORMAT_MAP.containsKey(patternPart)){
                    if(patternPart.equals(ApacheLoggingConstants.FIRST_REQUEST_LINE)){
                        if(!logParts.get(logPartCounter)
                                .equals(Utils.encloseStringInDoubleQuotes(Constants.STRING_SEPARATOR.DASH.getValue()))) {
                            logEntry.setFirstRequestLine(String.join(Constants.STRING_SEPARATOR.SPACE.getValue(),
                                    logParts.get(logPartCounter), logParts.get(++logPartCounter), logParts.get(++logPartCounter)));
                        } else {
                            logEntry.setValueByApachePlaceholder(logParts.get(logPartCounter),patternPart);
                        }

                    } else if(patternPart.equals(ApacheLoggingConstants.DATE_TIME)){
                        logEntry.setDateTime(String.join(Constants.STRING_SEPARATOR.SPACE.getValue(),
                                logParts.get(logPartCounter), logParts.get(++logPartCounter)));
                    }else{
                        logEntry.setValueByApachePlaceholder(logParts.get(logPartCounter),patternPart);
                    }
                }
                logPartCounter++;
            }


        } catch (Exception e){
            AppLogger.logSevere("Exception in LogWatcher.getPatternData", e);
            throw e;
        }
        return logEntry;
    }

    private static List<String> getUniqueRequestMethodsFromLogEntries(List<LogEntryDTO> logEntries) {
        try {
            return logEntries.parallelStream()
                    .map(LogEntryDTO::getRequestMethod)
                    .distinct()
                    .collect(Collectors.toList());
        } catch (Exception e) {
            AppLogger.logSevere("Exception in LogWatcher.getUniqueRequestMethodsFromLogEntries", e);
            return null;
        }
    }

    private static Date getAccessLogDateFromLogEntryDTO(LogEntryDTO logEntryDTO) {
        Date accessLogDate = new Date();
        try {
            accessLogDate = logEntryDTO.getDateTime();
        } catch (Exception e){
            AppLogger.logSevere("Exception in LogWatcher.getAccessLogDateFromLogEntryDTO", e);
        }
        return accessLogDate;
    }

    public static ActionDTO filterAccessLogEntries(String sql){
        ActionDTO actionDTO = new ActionDTO();
        actionDTO.setIsSuccessful(false);
        try {

        } catch (Exception e){
            AppLogger.logSevere("Exception in LogWatcher.filterAccessLogEntries", e);
        }
        return actionDTO;
    }
}
