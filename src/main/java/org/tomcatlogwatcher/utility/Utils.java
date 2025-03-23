package org.tomcatlogwatcher.utility;

import java.util.List;

public class Utils {

    public static boolean areStringsValid(String... str) {
        if (str == null) {
            return false;
        }
        for (String s : str) {
            if (s == null || s.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public static boolean areStringsValidIncludingBlankSpace(String... str) {
        if (str == null) {
            return false;
        }
        for (String s : str) {
            if (s == null || s.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public static boolean areStringsValid(List<String> strs) {
        if (isListNotEmpty(strs)) {
            return areStringsValid(strs.toArray(new String[0]));
        }
        return false;
    }

    public static boolean isListNotEmpty(List<?> objs){
        return objs!=null && !objs.isEmpty();
    }

    public static String encloseStringInDoubleQuotes(String str) {
        return "\""+str+"\"";
    }

    public static String removeStringDoubleQuotes(String str) {
        return str.replace("\"", "");
    }

}
