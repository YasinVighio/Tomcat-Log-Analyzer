package org.tomcatlogwatcher.utility;

import org.tomcatlogwatcher.core.ApacheLoggingConstants;

import java.util.Date;

public class ApacheUtil {

    public static Class<?> getClassByApachePlaceholder(String apachePlaceholder){
        if(Utils.areStringsValid(apachePlaceholder)){
            switch(apachePlaceholder){
                case ApacheLoggingConstants.BYTES_SENT_WITH_HEADERS:
                case ApacheLoggingConstants.BYTES_SENT_WITHOUT_HEADERS:
                case ApacheLoggingConstants.REQUEST_PROCESS_TIME_MS:
                case ApacheLoggingConstants.REQUEST_PROCESS_TIME_S:
                case ApacheLoggingConstants.COMMIT_RESPONSE_TIME_MS:
                    return Double.class;
                case ApacheLoggingConstants.REMOTE_HOST:
                case ApacheLoggingConstants.REQUEST_PROTOCOL:
                case ApacheLoggingConstants.REMOTE_LOGICAL_USERNAME:
                case ApacheLoggingConstants.REQUEST_METHOD:
                case ApacheLoggingConstants.QUERY_STRING:
                case ApacheLoggingConstants.FIRST_REQUEST_LINE:
                case ApacheLoggingConstants.USER_SESSION_ID:
                case ApacheLoggingConstants.REMOTE_AUTHENTICATED_USER:
                case ApacheLoggingConstants.CURRENT_REQUEST_THREAD:
                case ApacheLoggingConstants.LOCAL_IP:
                case ApacheLoggingConstants.REQUESTED_URL_PATH:
                case ApacheLoggingConstants.REMOTE_IP:
                case ApacheLoggingConstants.LOCAL_SERVER_NAME:
                    return String.class;
                case ApacheLoggingConstants.LOCAL_PORT:
                case ApacheLoggingConstants.HTTP_STATUS_CODE:
                    return Integer.class;
                case ApacheLoggingConstants.DATE_TIME:
                case ApacheLoggingConstants.REQUEST_START_TIME:
                    return Date.class;
                default:
                    return null;
            }
        }
        return null;
    }
}
