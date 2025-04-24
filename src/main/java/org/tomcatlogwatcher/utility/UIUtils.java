package org.tomcatlogwatcher.utility;

import org.tomcatlogwatcher.data.Constants;
import org.tomcatlogwatcher.core.PropManager;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;


public class UIUtils {


    public static RowFilter.ComparisonType getComparisonType(String comparison) {
        if(Utils.areStringsValid(comparison)){
            switch (comparison) {
                case "NE":
                    return RowFilter.ComparisonType.NOT_EQUAL;
                case "EQ":
                    return RowFilter.ComparisonType.EQUAL;
                case "LT":
                    return RowFilter.ComparisonType.BEFORE;
                case "GT":
                    return RowFilter.ComparisonType.AFTER;
            }
        }
        return RowFilter.ComparisonType.NOT_EQUAL;
    }

    public static Date getDateFromInputDateString(String date, Date accessLogDate) {
        Date dateToReturn = null;
        String timeFormat = Constants.DATE_FORMATS.HH_mm_ss.getValue();
        String accessLogStrDate = DateUtil.convertDateToString(accessLogDate, Constants.DATE_FORMATS.dd_MM_YYYY.getValue());
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
