package org.tomcatlogwatcher.data;

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
}
