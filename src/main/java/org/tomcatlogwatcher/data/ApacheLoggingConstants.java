package org.tomcatlogwatcher.data;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public interface ApacheLoggingConstants {

    String REMOTE_IP = "%a";
    String LOCAL_IP = "%A";
    String BYTES_SENT_WITH_HEADERS = "%b";
    String BYTES_SENT_WITHOUT_HEADERS = "%B";
    String REMOTE_HOST = "%h";
    String REQUEST_PROTOCOL = "%H";
    String REMOTE_LOGICAL_USERNAME = "%l";
    String REQUEST_METHOD = "%m";
    String LOCAL_PORT = "%p";
    String QUERY_STRING = "%q";
    String FIRST_REQUEST_LINE = "%r";
    String HTTP_STATUS_CODE = "%s";
    String USER_SESSION_ID = "%S";
    String DATE_TIME = "%t";
    String REMOTE_AUTHENTICATED_USER = "%u";
    String REQUESTED_URL_PATH = "%U";
    String LOCAL_SERVER_NAME = "%v";
    String REQUEST_PROCESS_TIME_MS = "%D";
    String REQUEST_PROCESS_TIME_S = "%T";
    String COMMIT_RESPONSE_TIME_MS = "%F";
    String CURRENT_REQUEST_THREAD = "%I";



    // Descriptions
    String DESC_REMOTE_IP = "Remote IP Address";
    String DESC_LOCAL_IP = "Local IP Address";
    String DESC_BYTES_SENT_WITH_HEADERS = "Bytes sent - HTTP headers";
    String DESC_BYTES_SENT_WITHOUT_HEADERS = "Bytes sent";
    String DESC_REMOTE_HOST = "Remote Host Name";
    String DESC_REQUEST_PROTOCOL = "Request Protocol";
    String DESC_REMOTE_LOGICAL_USERNAME = "Remote Logical Username";
    String DESC_REQUEST_METHOD = "Request Method";
    String DESC_LOCAL_PORT = "Local Port";
    String DESC_QUERY_STRING = "Query String";
    String DESC_FIRST_REQUEST_LINE = "Request";
    String DESC_HTTP_STATUS_CODE = "HTTP Status Code";
    String DESC_USER_SESSION_ID = "User Session ID";
    String DESC_DATE_TIME = "Request End Time";
    String DESC_REMOTE_AUTHENTICATED_USER = "Remote Authenticated User";
    String DESC_REQUESTED_URL_PATH = "Requested URL Path";
    String DESC_LOCAL_SERVER_NAME = "Local Server Name";
    String DESC_REQUEST_PROCESS_TIME_MS = "Request Process Time (milliseconds)";
    String DESC_REQUEST_PROCESS_TIME_S = "Request Process Time (seconds)";
    String DESC_COMMIT_RESPONSE_TIME_MS = "Commit Response Time (milliseconds)";
    String DESC_CURRENT_REQUEST_THREAD = "Current Request Thread";

    // db table cols
    String REMOTE_IP_COL = "remote_ip";
    String LOCAL_IP_COL = "local_ip";
    String BYTES_SENT_HEADER_COL = "bytes_sent_header";
    String BYTES_SENT_COL = "bytes_sent";
    String REMOTE_HOST_COL = "remote_host";
    String REQ_PROTOCOL_COL = "req_protocol";
    String REMOTE_LOGICAL_UNAME_COL = "remote_logical_username";
    String REQ_METHOD_COL = "req_method";
    String LOCAL_PORT_COL = "local_port";
    String QUERY_STR_COL = "query_string";
    String FIRST_LINE_REQ_COL = "req";
    String STS_CODE_COL = "http_status_code";
    String USESSION_ID_COL = "user_session_id";
    String END_TIME_COL = "req_end_time";
    String AUTH_USER_COL = "remote_auth_user";
    String REQ_URL_PATH_COL = "req_url_path";
    String LCL_SERVER_NAME_COL = "local_server_name";
    String REQ_PROCESS_MS_COL = "req_process_ms";
    String REQ_PROCESS_S_COL = "req_process_sec";
    String COMMIT_RESPONSE_TIME_MS_COL = "commit_response_time_ms";
    String CURRENT_REQ_THREAD_COL = "current_req_thread";


    //custom
    String REQUEST_START_TIME = "%requestStartTime";
    String DESC_REQUEST_START_TIME = "Request Start Time";
    String REQ_START_TIME_COL = "req_start_time";

    // Immutable Map of log format specifiers and their DB column
    Map<String, String> LOG_DB_COL_MAP = Collections.unmodifiableMap(new HashMap<String, String>() {{
        put(REMOTE_IP, REMOTE_IP_COL);
        put(LOCAL_IP, LOCAL_IP_COL);
        put(BYTES_SENT_WITH_HEADERS, BYTES_SENT_HEADER_COL);
        put(BYTES_SENT_WITHOUT_HEADERS, BYTES_SENT_COL);
        put(REMOTE_HOST, REMOTE_HOST_COL);
        put(REQUEST_PROTOCOL, REQ_PROTOCOL_COL);
        put(REMOTE_LOGICAL_USERNAME, REMOTE_LOGICAL_UNAME_COL);
        put(REQUEST_METHOD, REQ_METHOD_COL);
        put(LOCAL_PORT, LOCAL_PORT_COL);
        put(QUERY_STRING, QUERY_STR_COL);
        put(FIRST_REQUEST_LINE, FIRST_LINE_REQ_COL);
        put(HTTP_STATUS_CODE, STS_CODE_COL);
        put(USER_SESSION_ID, USESSION_ID_COL);
        put(DATE_TIME, END_TIME_COL);
        put(REMOTE_AUTHENTICATED_USER, AUTH_USER_COL);
        put(REQUESTED_URL_PATH, REQ_URL_PATH_COL);
        put(LOCAL_SERVER_NAME, LCL_SERVER_NAME_COL);
        put(REQUEST_PROCESS_TIME_MS, REQ_PROCESS_MS_COL);
        put(REQUEST_PROCESS_TIME_S, REQ_PROCESS_S_COL);
        put(COMMIT_RESPONSE_TIME_MS, COMMIT_RESPONSE_TIME_MS_COL);
        put(CURRENT_REQUEST_THREAD, CURRENT_REQ_THREAD_COL);
        put(REQUEST_START_TIME, REQ_START_TIME_COL);
    }});

    Map<String, String> LOG_DB_COL_DESC_MAP = Collections.unmodifiableMap(new HashMap<String, String>() {{
        put(REMOTE_IP_COL, DESC_REMOTE_IP);
        put(LOCAL_IP_COL, DESC_LOCAL_IP);
        put(BYTES_SENT_HEADER_COL, DESC_BYTES_SENT_WITH_HEADERS);
        put(BYTES_SENT_COL, DESC_BYTES_SENT_WITHOUT_HEADERS);
        put(REMOTE_HOST_COL, DESC_REMOTE_HOST);
        put(REQ_PROTOCOL_COL, DESC_REQUEST_PROTOCOL);
        put(REMOTE_LOGICAL_UNAME_COL, DESC_REMOTE_LOGICAL_USERNAME);
        put(REQ_METHOD_COL, DESC_REQUEST_METHOD);
        put(LOCAL_PORT_COL, DESC_LOCAL_PORT);
        put(QUERY_STR_COL, DESC_QUERY_STRING);
        put(FIRST_LINE_REQ_COL, DESC_FIRST_REQUEST_LINE);
        put(STS_CODE_COL, DESC_HTTP_STATUS_CODE);
        put(USESSION_ID_COL, DESC_USER_SESSION_ID);
        put(END_TIME_COL, DESC_DATE_TIME);
        put(AUTH_USER_COL, DESC_REMOTE_AUTHENTICATED_USER);
        put(REQ_URL_PATH_COL, DESC_REQUESTED_URL_PATH);
        put(LCL_SERVER_NAME_COL, DESC_LOCAL_SERVER_NAME);
        put(REQ_PROCESS_MS_COL, DESC_REQUEST_PROCESS_TIME_MS);
        put(REQ_PROCESS_S_COL, DESC_REQUEST_PROCESS_TIME_S);
        put(COMMIT_RESPONSE_TIME_MS_COL, DESC_COMMIT_RESPONSE_TIME_MS);
        put(CURRENT_REQ_THREAD_COL, DESC_CURRENT_REQUEST_THREAD);
        put(REQ_START_TIME_COL, DESC_REQUEST_START_TIME);
    }});


    // Immutable Map of log format specifiers and their descriptions
    Map<String, String> LOG_FORMAT_MAP = Collections.unmodifiableMap(new HashMap<String, String>() {{
        put(REMOTE_IP, DESC_REMOTE_IP);
        put(LOCAL_IP, DESC_LOCAL_IP);
        put(BYTES_SENT_WITH_HEADERS, DESC_BYTES_SENT_WITH_HEADERS);
        put(BYTES_SENT_WITHOUT_HEADERS, DESC_BYTES_SENT_WITHOUT_HEADERS);
        put(REMOTE_HOST, DESC_REMOTE_HOST);
        put(REQUEST_PROTOCOL, DESC_REQUEST_PROTOCOL);
        put(REMOTE_LOGICAL_USERNAME, DESC_REMOTE_LOGICAL_USERNAME);
        put(REQUEST_METHOD, DESC_REQUEST_METHOD);
        put(LOCAL_PORT, DESC_LOCAL_PORT);
        put(QUERY_STRING, DESC_QUERY_STRING);
        put(FIRST_REQUEST_LINE, DESC_FIRST_REQUEST_LINE);
        put(HTTP_STATUS_CODE, DESC_HTTP_STATUS_CODE);
        put(USER_SESSION_ID, DESC_USER_SESSION_ID);
        put(DATE_TIME, DESC_DATE_TIME);
        put(REMOTE_AUTHENTICATED_USER, DESC_REMOTE_AUTHENTICATED_USER);
        put(REQUESTED_URL_PATH, DESC_REQUESTED_URL_PATH);
        put(LOCAL_SERVER_NAME, DESC_LOCAL_SERVER_NAME);
        put(REQUEST_PROCESS_TIME_MS, DESC_REQUEST_PROCESS_TIME_MS);
        put(REQUEST_PROCESS_TIME_S, DESC_REQUEST_PROCESS_TIME_S);
        put(COMMIT_RESPONSE_TIME_MS, DESC_COMMIT_RESPONSE_TIME_MS);
        put(CURRENT_REQUEST_THREAD, DESC_CURRENT_REQUEST_THREAD);
    }});

    Map<String, Class> LOG_DATA_TYPE_MAP = Collections.unmodifiableMap(new HashMap<String, Class>() {{
        put(REMOTE_IP, String.class);
        put(LOCAL_IP, String.class);
        put(BYTES_SENT_WITH_HEADERS, Integer.class);
        put(BYTES_SENT_WITHOUT_HEADERS, Integer.class);
        put(REMOTE_HOST, String.class);
        put(REQUEST_PROTOCOL, String.class);
        put(REMOTE_LOGICAL_USERNAME, String.class);
        put(REQUEST_METHOD, String.class);
        put(LOCAL_PORT, String.class);
        put(QUERY_STRING, String.class);
        put(FIRST_REQUEST_LINE, String.class);
        put(HTTP_STATUS_CODE, Integer.class);
        put(USER_SESSION_ID, String.class);
        put(REQUEST_START_TIME, Date.class);
        put(DATE_TIME, Date.class);
        put(REMOTE_AUTHENTICATED_USER, String.class);
        put(REQUESTED_URL_PATH, String.class);
        put(LOCAL_SERVER_NAME, String.class);
        put(REQUEST_PROCESS_TIME_MS, Double.class);
        put(REQUEST_PROCESS_TIME_S, Double.class);
        put(COMMIT_RESPONSE_TIME_MS, Double.class);
        put(CURRENT_REQUEST_THREAD, String.class);
    }});
}
