package org.tomcatlogwatcher.dto;

import lombok.*;
import org.tomcatlogwatcher.data.ApacheLoggingConstants;
import org.tomcatlogwatcher.data.Constants;
import org.tomcatlogwatcher.utility.DateUtil;
import org.tomcatlogwatcher.utility.Utils;

import java.time.Instant;
import java.util.Date;
import java.util.stream.Stream;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LogEntryDTO {
    private String remoteIp;
    private String localIp;
    private Integer bytesSentWithHeaders;
    private Integer bytesSentWithoutHeaders;
    private String remoteHost;
    private String requestProtocol;
    private String remoteLogicalUsername;
    private String requestMethod;
    private String localPort;
    private String queryString;
    private String firstRequestLine;
    private Integer httpStatusCode;
    private String userSessionId;
    private Date dateTime;
    private String remoteAuthenticatedUser;
    private String requestedUrlPath;
    private String localServerName;
    private Double requestProcessTimeMs;
    private Double requestProcessTimeS;
    private Double commitResponseTimeMs;
    private String currentRequestThread;
    private Date requestStartTime;


    public void setRequestProcessTimeS(String requestProcessTimeS){
        this.requestProcessTimeS = Double.parseDouble(requestProcessTimeS);
        if(this.dateTime!=null){
            this.requestStartTime = calculateRequestStartTime();
        }
    }

    public void setDateTime(String dateTime){

        //clear extras in date

        dateTime = dateTime.replace(Constants.STRING_SEPARATOR.SQUARE_BRACKET_OPEN.getValue(), "")
                .replace(Constants.STRING_SEPARATOR.SQUARE_BRACKET_CLOSE.getValue(), "")
                .trim();

        //remove time zone

        String timeZoneOffset = Stream.of(
                        Constants.TIME_ZONE_OFFSET.POSITIVE.getValue(),
                        Constants.TIME_ZONE_OFFSET.NEGATIVE.getValue())
                .filter(dateTime::contains)
                .findFirst()
                .orElse(Constants.STRING_SEPARATOR.SPACE.getValue());

        String timeZone = dateTime.substring(dateTime.indexOf(timeZoneOffset));

        dateTime = dateTime.substring(0, dateTime.indexOf(timeZoneOffset)).trim();

        DateUtil.setDefaultJVMTimeZone(timeZone);
        //parse
        this.dateTime = DateUtil.getDateFromStringDate(dateTime, Constants.DATE_FORMATS.COMMON_LOGGING_FORMAT_WITHOUT_ZONE.getValue());

        if(this.requestProcessTimeS!=null){
            this.requestStartTime = calculateRequestStartTime();
        }
    }

    public void setBytesSentWithHeaders(String bytesSentWithHeaders){
        if(Utils.areStringsValid(bytesSentWithHeaders)){
            if(bytesSentWithHeaders.contains("-")){
                bytesSentWithHeaders = "0";
            }
            this.bytesSentWithHeaders = Integer.parseInt(bytesSentWithHeaders);
        }
    }

    private Date calculateRequestStartTime() {
        long secondsPart = requestProcessTimeS.longValue();
        int nanosPart = (int) ((requestProcessTimeS - secondsPart) * 1_000_000_000);

        Instant startInstant = this.dateTime.toInstant().minusSeconds(secondsPart).minusNanos(nanosPart);

        return Date.from(startInstant);
    }

    public void setFirstRequestLine(String firstRequestLine){
        String[] firstLineReqParts = firstRequestLine.split(Constants.STRING_SEPARATOR.SPACE.getValue());
        this.requestMethod = Utils.removeStringDoubleQuotes(firstLineReqParts[0]);
        this.firstRequestLine = firstLineReqParts[1];
    }

    public Object getValueByApachePlaceholder(String columnApacheValue) {
        if (Utils.areStringsValid(columnApacheValue)) {
            switch (columnApacheValue) {
                case ApacheLoggingConstants.BYTES_SENT_WITH_HEADERS:
                    return bytesSentWithHeaders;
                case ApacheLoggingConstants.BYTES_SENT_WITHOUT_HEADERS:
                    return bytesSentWithoutHeaders;
                case ApacheLoggingConstants.REMOTE_HOST:
                    return remoteHost;
                case ApacheLoggingConstants.REQUEST_PROTOCOL:
                    return requestProtocol;
                case ApacheLoggingConstants.REMOTE_LOGICAL_USERNAME:
                    return remoteLogicalUsername;
                case ApacheLoggingConstants.REQUEST_METHOD:
                    return requestMethod;
                case ApacheLoggingConstants.LOCAL_PORT:
                    return localPort;
                case ApacheLoggingConstants.QUERY_STRING:
                    return queryString;
                case ApacheLoggingConstants.FIRST_REQUEST_LINE:
                    return firstRequestLine;
                case ApacheLoggingConstants.HTTP_STATUS_CODE:
                    return httpStatusCode;
                case ApacheLoggingConstants.USER_SESSION_ID:
                    return userSessionId;
                case ApacheLoggingConstants.DATE_TIME:
                    return dateTime;
                case ApacheLoggingConstants.REMOTE_AUTHENTICATED_USER:
                    return remoteAuthenticatedUser;
                case ApacheLoggingConstants.REQUEST_PROCESS_TIME_MS:
                    return requestProcessTimeMs;
                case ApacheLoggingConstants.REQUEST_PROCESS_TIME_S:
                    return requestProcessTimeS;
                case ApacheLoggingConstants.COMMIT_RESPONSE_TIME_MS:
                    return commitResponseTimeMs;
                case ApacheLoggingConstants.CURRENT_REQUEST_THREAD:
                    return currentRequestThread;
                case ApacheLoggingConstants.LOCAL_IP:
                    return localIp;
                case ApacheLoggingConstants.REMOTE_IP:
                    return remoteIp;
                case ApacheLoggingConstants.LOCAL_SERVER_NAME:
                    return localServerName;
                case ApacheLoggingConstants.REQUESTED_URL_PATH:
                    return requestedUrlPath;
                case ApacheLoggingConstants.REQUEST_START_TIME:
                    return requestStartTime;
                default:
                    return null;
            }
        }
        return null;
    }

    public void setValueByApachePlaceholder(Object value, String columnApacheValue) {
        if (Utils.areStringsValid(columnApacheValue)) {
            switch (columnApacheValue) {
                case ApacheLoggingConstants.BYTES_SENT_WITH_HEADERS:
                    setBytesSentWithHeaders(value.toString());
                    break;
                case ApacheLoggingConstants.BYTES_SENT_WITHOUT_HEADERS:
                    this.bytesSentWithoutHeaders = Integer.valueOf(value.toString());
                    break;
                case ApacheLoggingConstants.REMOTE_HOST:
                    this.remoteHost = value.toString();
                    break;
                case ApacheLoggingConstants.REQUEST_PROTOCOL:
                    this.requestProtocol = value.toString();
                    break;
                case ApacheLoggingConstants.REMOTE_LOGICAL_USERNAME:
                    this.remoteLogicalUsername= value.toString();
                    break;
                case ApacheLoggingConstants.REQUEST_METHOD:
                    this.requestMethod= value.toString();
                    break;
                case ApacheLoggingConstants.LOCAL_PORT:
                    this.localPort= value.toString();
                    break;
                case ApacheLoggingConstants.QUERY_STRING:
                    this.queryString= value.toString();
                    break;
                case ApacheLoggingConstants.FIRST_REQUEST_LINE:
                    this.firstRequestLine= value.toString();
                    break;
                case ApacheLoggingConstants.HTTP_STATUS_CODE:
                    this.httpStatusCode= Integer.valueOf(value.toString());
                    break;
                case ApacheLoggingConstants.USER_SESSION_ID:
                    this.userSessionId= value.toString();
                    break;
                case ApacheLoggingConstants.DATE_TIME:
                    setDateTime(value.toString());
                    break;
                case ApacheLoggingConstants.REMOTE_AUTHENTICATED_USER:
                    this.remoteAuthenticatedUser= value.toString();
                    break;
                case ApacheLoggingConstants.REQUEST_PROCESS_TIME_MS:
                    this.requestProcessTimeMs = Double.valueOf(value.toString());
                    break;
                case ApacheLoggingConstants.REQUEST_PROCESS_TIME_S:
                    setRequestProcessTimeS(value.toString());
                    break;
                case ApacheLoggingConstants.COMMIT_RESPONSE_TIME_MS:
                    this.commitResponseTimeMs = Double.valueOf(value.toString());
                    break;
                case ApacheLoggingConstants.CURRENT_REQUEST_THREAD:
                    this.currentRequestThread = value.toString();
                    break;
                case ApacheLoggingConstants.LOCAL_IP:
                    this.localIp = value.toString();
                    break;
                case ApacheLoggingConstants.REMOTE_IP:
                    this.remoteIp = value.toString();
                    break;
                case ApacheLoggingConstants.LOCAL_SERVER_NAME:
                    this.localServerName = value.toString();
                    break;
                case ApacheLoggingConstants.REQUESTED_URL_PATH:
                    this.requestedUrlPath = value.toString();
                    break;
            }
        }
    }
}

