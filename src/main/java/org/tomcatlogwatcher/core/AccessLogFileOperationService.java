package org.tomcatlogwatcher.core;

import org.tomcatlogwatcher.data.ApacheLoggingConstants;
import org.tomcatlogwatcher.data.Constants;
import org.tomcatlogwatcher.dto.ActionDTO;
import org.tomcatlogwatcher.dto.LogEntryDTO;
import org.tomcatlogwatcher.utility.AppLogger;
import org.tomcatlogwatcher.utility.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class AccessLogFileOperationService {

    public static ActionDTO loadAccessLogFile(String accessLogFileName, String logPattern) {
        ActionDTO actionRes = new ActionDTO();
        actionRes.setIsSuccessful(false);
        try{
            List<String> patternParts = new ArrayList<>(readPattern(logPattern));

            List<LogEntryDTO> logEntries = readAccessLogFile(accessLogFileName, patternParts);

            int dateTimeIndex = patternParts.indexOf(ApacheLoggingConstants.DATE_TIME);
            if (dateTimeIndex != -1) {
                patternParts.add(dateTimeIndex, ApacheLoggingConstants.REQUEST_START_TIME);
            }

            patternParts = patternParts.stream().filter(ptrn -> ptrn.startsWith("%")).collect(Collectors.toList());

            AccessLogDbOperationService.createLogTable(patternParts);
            AccessLogDbOperationService.insertLogEntries(logEntries, patternParts);

            actionRes.setIsSuccessful(true);
            actionRes.setMessage("Successfully loaded access log file");
        } catch (Exception e) {
            AppLogger.logSevere("Exception in AccessLogFileOperationService.loadAccessLogFile()", e);
            actionRes.setIsSuccessful(false);
            actionRes.setMessage("Error loading access log file, check logs");
        }
        return actionRes;
    }

    private static List<LogEntryDTO> readAccessLogFile(String accessFile, List<String> patternParts) throws Exception {
        List<LogEntryDTO> logEntries = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(accessFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                logEntries.add(getLogEntryFromLogString(line, patternParts));
            }
        } catch (Exception e) {
            AppLogger.logSevere("Exception in AccessLogFileOperationService.readAccessLogs", e);
            throw e;
        }
        return logEntries;
    }

    private static List<String> readPattern(String pattern) {
        List<String> patternParts;
        try {
            pattern = pattern.trim();
            patternParts = Arrays.asList(pattern.split(" "));
            patternParts.replaceAll(string -> string.replace("\"", "").trim());
        } catch (Exception e){
            AppLogger.logSevere("Exception in AccessLogFileOperationService.readPattern", e);
            throw e;
        }
        return patternParts;
    }

    private static LogEntryDTO getLogEntryFromLogString(String logEntryString, List<String> patternParts) {
        LogEntryDTO logEntry = new LogEntryDTO();
        try{
            List<String> logParts = Arrays.asList(logEntryString.trim().split(" "));

            int logPartCounter = 0;

            for(String patternPart : patternParts) {
                if (patternPart.equals(ApacheLoggingConstants.FIRST_REQUEST_LINE)) {
                    if (!logParts.get(logPartCounter)
                            .equals(Utils.encloseStringInDoubleQuotes(Constants.STRING_SEPARATOR.DASH.getValue()))) {
                        logEntry.setFirstRequestLine(String.join(Constants.STRING_SEPARATOR.SPACE.getValue(),
                                logParts.get(logPartCounter), logParts.get(++logPartCounter), logParts.get(++logPartCounter)));
                    } else {
                        logEntry.setValueByApachePlaceholder(logParts.get(logPartCounter), patternPart);
                    }

                } else if (patternPart.equals(ApacheLoggingConstants.DATE_TIME)) {
                    logEntry.setDateTime(String.join(Constants.STRING_SEPARATOR.SPACE.getValue(),
                            logParts.get(logPartCounter), logParts.get(++logPartCounter)));
                } else {
                    logEntry.setValueByApachePlaceholder(logParts.get(logPartCounter), patternPart);
                }
                logPartCounter++;
            }
        } catch (Exception e){
            AppLogger.logSevere("Exception in AccessLogFileOperationService.getPatternData", e);
            throw e;
        }
        return logEntry;
    }

}
