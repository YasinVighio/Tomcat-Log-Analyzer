package org.tomcatlogwatcher.utility;

import java.util.Collection;
import java.util.Date;

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

    public static boolean isCollectionNotEmpty(Collection coll) {
        return coll != null && !coll.isEmpty();
    }

    public static String encloseStringInDoubleQuotes(String str) {
        return "\""+str+"\"";
    }

    public static String removeStringDoubleQuotes(String str) {
        return str.replace("\"", "");
    }


    public static Class<?> getJavaTypeForH2Type(String h2Type) {
        if (h2Type == null) return Object.class;

        switch (h2Type.toUpperCase()) {
            case "VARCHAR":
            case "VARCHAR_IGNORECASE":
            case "CHAR":
            case "TEXT":
            case "CLOB":
                return String.class;
            case "BOOLEAN":
                return Boolean.class;
            case "TINYINT":
            case "SMALLINT":
            case "INTEGER":
            case "INT":
                return Integer.class;
            case "BIGINT":
                return Long.class;
            case "DECIMAL":
            case "NUMERIC":
                return java.math.BigDecimal.class;
            case "REAL":
                return Float.class;
            case "DOUBLE":
            case "FLOAT":
            case "DOUBLE PRECISION":
                return Double.class;
            case "DATE":
            case "TIME":
            case "TIMESTAMP":
            case "DATETIME":
                return Date.class;
            case "BINARY":
            case "VARBINARY":
            case "BLOB":
                return byte[].class;
            default:
                return Object.class;
        }
    }

}
