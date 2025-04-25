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

    public static boolean validateDQL(String dql) {
        if(Utils.areStringsValid(dql)){
            return dql.toUpperCase().startsWith("SELECT");
        }
        return false;
    }

}
