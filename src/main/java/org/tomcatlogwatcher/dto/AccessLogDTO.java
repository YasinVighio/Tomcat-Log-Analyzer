package org.tomcatlogwatcher.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AccessLogDTO {
    private List<String> headers;
    private List<String> columnApacheValues;
    private List<LogEntryDTO> logEntries;
    private List<String> requestMethods;
    private Date accessLogDate;
}
