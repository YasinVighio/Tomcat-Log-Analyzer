package org.tomcatlogwatcher.utility;

import org.tomcatlogwatcher.core.Constants;
import org.tomcatlogwatcher.core.PropManager;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;

public class DateUtil {
    public static Date getDateFromStringDate(String dateString, String format) {
        Date date = null;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            LocalDateTime localDateTime = LocalDateTime.parse(dateString, formatter);
            date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        } catch (Exception e) {
            AppLogger.logSevere("Error in Utils.getDateFromStringDate()", e);
        }
        return date;
    }

    public static String convertDateToString(Date date, String format) {
        String dateString = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            dateString = formatter.format(date);
        } catch (Exception e) {
            AppLogger.logSevere("Error in Utils.convertDateToString()", e);
        }
        return dateString;
    }

    public static Date getDateFromInputDateString(String date, Date accessLogDate) {
        Date dateToReturn = null;
        String timeFormat = Constants.DATE_FORMATS.HH_mm_ss.getValue();
        String accessLogStrDate = convertDateToString(accessLogDate, Constants.DATE_FORMATS.dd_MM_YYYY.getValue());
        try {
            List<String> allowedDateFormats = PropManager.getAllowedDateInputFormats();
            for (String allowedDateFormat : allowedDateFormats) {
                try {

                    if(allowedDateFormat.equals(timeFormat) && date.trim().length() == timeFormat.length()) {
                        date = String.join(Constants.STRING_SEPARATOR.SPACE.getValue(), accessLogStrDate, date);
                        allowedDateFormat = Constants.DATE_FORMATS.dd_MM_YYYY_HH_mm_ss.getValue();
                    }

                    DateTimeFormatter strictFormatter = new DateTimeFormatterBuilder()
                            .appendPattern(allowedDateFormat).toFormatter();

                    LocalDateTime dateTime = LocalDateTime.parse(date, strictFormatter);

                    dateToReturn = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
                    break;

                } catch (DateTimeParseException e) {
                    continue;
                }
            }
            if (dateToReturn == null) {
                throw new IllegalArgumentException("Invalid date format");
            }

        } catch (Exception e) {
            AppLogger.logSevere("Error in UIUtils.getDateFromInputDateString()", e);
        }
        return dateToReturn;
    }
}
