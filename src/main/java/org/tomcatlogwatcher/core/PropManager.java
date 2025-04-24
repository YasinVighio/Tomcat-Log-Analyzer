package org.tomcatlogwatcher.core;

import org.tomcatlogwatcher.data.Constants;
import org.tomcatlogwatcher.utility.AppLogger;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class PropManager {
    private static final Properties properties = new Properties();

    private static final String DEFAULT_PATTERN_PROP_NAME = "defaultPattern";
    private static final String ALLOWED_DATE_INPUT_FORMATS_PROP_NAME = "allowedDateInputFormats";

    private static final String START_TIME_DATE_FORMAT_PROP_NAME = "startTimeDateFormat";
    private static final String END_TIME_DATE_FORMAT_PROP_NAME = "endTimeDateFormat";

    private static final String PROP_FILE_PATH = "configs/app.ini";

    public static void initProperties() throws Exception{
        try (FileInputStream input = new FileInputStream(PROP_FILE_PATH)) {
            properties.load(input);
        } catch (Exception e) {
            AppLogger.logSevere("Error in PropManager.initProperties", e);
            throw e;
        }
    }

    public static String getDefaultPattern() {
        String defaultPattern = Constants.DEFAULT_PATTERN;
        try {
            defaultPattern = properties.getProperty(DEFAULT_PATTERN_PROP_NAME);
        } catch (Exception e){
            AppLogger.logSevere("Error in PropManager.getPatchCreateSuccessMsg", e);
        }
        return defaultPattern;
    }

    public static List<String> getAllowedDateInputFormats() {
        List<String> allowedDateInputFormats = new ArrayList<String>();
        try {
            allowedDateInputFormats = Arrays.asList(properties.getProperty(ALLOWED_DATE_INPUT_FORMATS_PROP_NAME)
                    .split(Constants.STRING_SEPARATOR.COMMA.getValue()));
        } catch (Exception e){
            AppLogger.logSevere("Error in PropManager.getPatchCreateSuccessMsg", e);
        }
        return allowedDateInputFormats;
    }

    public static String getStartTimeDateFormat() {
        String defaultPattern = Constants.DATE_FORMATS.COMMON_LOGGING_FORMAT_WITH_MS_WITHOUT_ZONE.getValue();
        try {
            defaultPattern = properties.getProperty(START_TIME_DATE_FORMAT_PROP_NAME);
        } catch (Exception e){
            AppLogger.logSevere("Error in PropManager.getPatchCreateSuccessMsg", e);
        }
        return defaultPattern;
    }

    public static String getEndTimeDateFormat() {
        String defaultPattern = Constants.DATE_FORMATS.COMMON_LOGGING_FORMAT_WITHOUT_ZONE.getValue();
        try {
            defaultPattern = properties.getProperty(END_TIME_DATE_FORMAT_PROP_NAME);
        } catch (Exception e){
            AppLogger.logSevere("Error in PropManager.getPatchCreateSuccessMsg", e);
        }
        return defaultPattern;
    }

}
