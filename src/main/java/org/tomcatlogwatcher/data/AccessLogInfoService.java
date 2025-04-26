package org.tomcatlogwatcher.data;

import org.tomcatlogwatcher.dto.AccessLogInfoDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.tomcatlogwatcher.data.ApacheLoggingConstants.*;

public class AccessLogInfoService {

    public static final List<AccessLogInfoDTO> ACCESS_LOG_INFO_LIST = new ArrayList<>();

    static {

        ACCESS_LOG_INFO_LIST.add(AccessLogInfoDTO.builder()
                .apachePattern(REMOTE_IP)
                .description(DESC_REMOTE_IP)
                .dbColumnName(REMOTE_IP_COL)
                .javaType(String.class)
                .sqlType(Constants.H2_DB_TYPE.VARCHAR_TYPE.getValue())
                .build());

        ACCESS_LOG_INFO_LIST.add(AccessLogInfoDTO.builder()
                .apachePattern(LOCAL_IP)
                .description(DESC_LOCAL_IP)
                .dbColumnName(LOCAL_IP_COL)
                .javaType(String.class)
                .sqlType(Constants.H2_DB_TYPE.VARCHAR_TYPE.getValue())
                .build());

        ACCESS_LOG_INFO_LIST.add(AccessLogInfoDTO.builder()
                .apachePattern(BYTES_SENT_WITH_HEADERS)
                .description(DESC_BYTES_SENT_WITH_HEADERS)
                .dbColumnName(BYTES_SENT_HEADER_COL)
                .javaType(Integer.class)
                .sqlType(Constants.H2_DB_TYPE.INT_TYPE.getValue())
                .build());

        ACCESS_LOG_INFO_LIST.add(AccessLogInfoDTO.builder()
                .apachePattern(BYTES_SENT_WITHOUT_HEADERS)
                .description(DESC_BYTES_SENT_WITHOUT_HEADERS)
                .dbColumnName(BYTES_SENT_COL)
                .javaType(Integer.class)
                .sqlType(Constants.H2_DB_TYPE.INT_TYPE.getValue())
                .build());

        ACCESS_LOG_INFO_LIST.add(AccessLogInfoDTO.builder()
                .apachePattern(REMOTE_HOST)
                .description(DESC_REMOTE_HOST)
                .dbColumnName(REMOTE_HOST_COL)
                .javaType(String.class)
                .sqlType(Constants.H2_DB_TYPE.VARCHAR_TYPE.getValue())
                .build());

        ACCESS_LOG_INFO_LIST.add(AccessLogInfoDTO.builder()
                .apachePattern(REQUEST_PROTOCOL)
                .description(DESC_REQUEST_PROTOCOL)
                .dbColumnName(REQ_PROTOCOL_COL)
                .javaType(String.class)
                .sqlType(Constants.H2_DB_TYPE.VARCHAR_TYPE.getValue())
                .build());

        ACCESS_LOG_INFO_LIST.add(AccessLogInfoDTO.builder()
                .apachePattern(REMOTE_LOGICAL_USERNAME)
                .description(DESC_REMOTE_LOGICAL_USERNAME)
                .dbColumnName(REMOTE_LOGICAL_UNAME_COL)
                .javaType(String.class)
                .sqlType(Constants.H2_DB_TYPE.VARCHAR_TYPE.getValue())
                .build());

        ACCESS_LOG_INFO_LIST.add(AccessLogInfoDTO.builder()
                .apachePattern(REQUEST_METHOD)
                .description(DESC_REQUEST_METHOD)
                .dbColumnName(REQ_METHOD_COL)
                .javaType(String.class)
                .sqlType(Constants.H2_DB_TYPE.VARCHAR_TYPE.getValue())
                .build());

        ACCESS_LOG_INFO_LIST.add(AccessLogInfoDTO.builder()
                .apachePattern(LOCAL_PORT)
                .description(DESC_LOCAL_PORT)
                .dbColumnName(LOCAL_PORT_COL)
                .javaType(String.class)
                .sqlType(Constants.H2_DB_TYPE.VARCHAR_TYPE.getValue())
                .build());

        ACCESS_LOG_INFO_LIST.add(AccessLogInfoDTO.builder()
                .apachePattern(QUERY_STRING)
                .description(DESC_QUERY_STRING)
                .dbColumnName(QUERY_STR_COL)
                .javaType(String.class)
                .sqlType(Constants.H2_DB_TYPE.VARCHAR_TYPE.getValue())
                .build());

        ACCESS_LOG_INFO_LIST.add(AccessLogInfoDTO.builder()
                .apachePattern(FIRST_REQUEST_LINE)
                .description(DESC_FIRST_REQUEST_LINE)
                .dbColumnName(FIRST_LINE_REQ_COL)
                .javaType(String.class)
                .isLongText(true)
                .sqlType(Constants.H2_DB_TYPE.VARCHAR_TYPE.getValue())
                .build());

        ACCESS_LOG_INFO_LIST.add(AccessLogInfoDTO.builder()
                .apachePattern(HTTP_STATUS_CODE)
                .description(DESC_HTTP_STATUS_CODE)
                .dbColumnName(STS_CODE_COL)
                .javaType(Integer.class)
                .sqlType(Constants.H2_DB_TYPE.INT_TYPE.getValue())
                .build());

        ACCESS_LOG_INFO_LIST.add(AccessLogInfoDTO.builder()
                .apachePattern(USER_SESSION_ID)
                .description(DESC_USER_SESSION_ID)
                .dbColumnName(USESSION_ID_COL)
                .javaType(String.class)
                .sqlType(Constants.H2_DB_TYPE.VARCHAR_TYPE.getValue())
                .build());

        ACCESS_LOG_INFO_LIST.add(AccessLogInfoDTO.builder()
                .apachePattern(DATE_TIME)
                .description(DESC_DATE_TIME)
                .dbColumnName(END_TIME_COL)
                .javaType(Date.class)
                .sqlType(Constants.H2_DB_TYPE.TIMESTAMP_TYPE.getValue())
                .build());

        ACCESS_LOG_INFO_LIST.add(AccessLogInfoDTO.builder()
                .apachePattern(REMOTE_AUTHENTICATED_USER)
                .description(DESC_REMOTE_AUTHENTICATED_USER)
                .dbColumnName(AUTH_USER_COL)
                .javaType(String.class)
                .sqlType(Constants.H2_DB_TYPE.VARCHAR_TYPE.getValue())
                .build());

        ACCESS_LOG_INFO_LIST.add(AccessLogInfoDTO.builder()
                .apachePattern(REQUESTED_URL_PATH)
                .description(DESC_REQUESTED_URL_PATH)
                .dbColumnName(REQ_URL_PATH_COL)
                .javaType(String.class)
                .sqlType(Constants.H2_DB_TYPE.VARCHAR_TYPE.getValue())
                .build());

        ACCESS_LOG_INFO_LIST.add(AccessLogInfoDTO.builder()
                .apachePattern(LOCAL_SERVER_NAME)
                .description(DESC_LOCAL_SERVER_NAME)
                .dbColumnName(LCL_SERVER_NAME_COL)
                .javaType(String.class)
                .sqlType(Constants.H2_DB_TYPE.VARCHAR_TYPE.getValue())
                .build());

        ACCESS_LOG_INFO_LIST.add(AccessLogInfoDTO.builder()
                .apachePattern(REQUEST_PROCESS_TIME_MS)
                .description(DESC_REQUEST_PROCESS_TIME_MS)
                .dbColumnName(REQ_PROCESS_MS_COL)
                .javaType(Double.class)
                .sqlType(Constants.H2_DB_TYPE.DOUBLE_TYPE.getValue())
                .build());

        ACCESS_LOG_INFO_LIST.add(AccessLogInfoDTO.builder()
                .apachePattern(REQUEST_PROCESS_TIME_S)
                .description(DESC_REQUEST_PROCESS_TIME_S)
                .dbColumnName(REQ_PROCESS_S_COL)
                .javaType(Double.class)
                .sqlType(Constants.H2_DB_TYPE.DOUBLE_TYPE.getValue())
                .build());

        ACCESS_LOG_INFO_LIST.add(AccessLogInfoDTO.builder()
                .apachePattern(COMMIT_RESPONSE_TIME_MS)
                .description(DESC_COMMIT_RESPONSE_TIME_MS)
                .dbColumnName(COMMIT_RESPONSE_TIME_MS_COL)
                .javaType(Double.class)
                .sqlType(Constants.H2_DB_TYPE.DOUBLE_TYPE.getValue())
                .build());

        ACCESS_LOG_INFO_LIST.add(AccessLogInfoDTO.builder()
                .apachePattern(CURRENT_REQUEST_THREAD)
                .description(DESC_CURRENT_REQUEST_THREAD)
                .dbColumnName(CURRENT_REQ_THREAD_COL)
                .javaType(String.class)
                .sqlType(Constants.H2_DB_TYPE.VARCHAR_TYPE.getValue())
                .build());

        // Custom field
        ACCESS_LOG_INFO_LIST.add(AccessLogInfoDTO.builder()
                .apachePattern(REQUEST_START_TIME)
                .description(DESC_REQUEST_START_TIME)
                .dbColumnName(REQ_START_TIME_COL)
                .isCustom(true)
                .javaType(Date.class)
                .sqlType(Constants.H2_DB_TYPE.TIMESTAMP_TYPE.getValue())
                .build());
    }

    public static AccessLogInfoDTO getAccessLogInfoByPattern(String pattern) {
        return ACCESS_LOG_INFO_LIST.stream()
                .filter(dto -> dto.getApachePattern().equals(pattern))
                .findFirst()
                .orElse(null);
    }

    public static AccessLogInfoDTO getAccessLogInfoByDescription(String description, boolean caseSensitive) {
        return ACCESS_LOG_INFO_LIST.stream()
                .filter(dto -> caseSensitive
                        ? dto.getDescription().equals(description)
                        : dto.getDescription().equalsIgnoreCase(description))
                .findFirst()
                .orElse(null);
    }

    public static AccessLogInfoDTO getAccessLogInfoByDbColumn(String dbColumnName, boolean caseSensitive) {
        return ACCESS_LOG_INFO_LIST.stream()
                .filter(dto -> caseSensitive
                        ? dto.getDbColumnName().equals(dbColumnName)
                        : dto.getDbColumnName().equalsIgnoreCase(dbColumnName))
                .findFirst()
                .orElse(null);
    }

    public static List<AccessLogInfoDTO> getAccessLogInfoDTOsByPatterns(List<String> patterns) {
        return patterns.stream()
                .map(pattern -> ACCESS_LOG_INFO_LIST.stream()
                        .filter(dto -> dto.getApachePattern().equals(pattern))
                        .findFirst()
                        .orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

}
