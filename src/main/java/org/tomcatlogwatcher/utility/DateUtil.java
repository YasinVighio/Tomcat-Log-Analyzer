package org.tomcatlogwatcher.utility;

import org.tomcatlogwatcher.TomcatLogWatcher;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

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

    public static void setDefaultJVMTimeZone(String offsetStr){
        try {
            if(!TomcatLogWatcher.isDefaultJVMTimeZoneSet) {
                ZoneOffset offset = ZoneOffset.of(offsetStr);
                String gmtOffset = "GMT" + offset.getId();
                TimeZone tz = TimeZone.getTimeZone(gmtOffset);
                TimeZone.setDefault(tz);
                TomcatLogWatcher.isDefaultJVMTimeZoneSet = true;
            }
        } catch (Exception e) {
            AppLogger.logSevere("Error in Utils.getDateFromInputDateString()", e);
        }
    }
}
