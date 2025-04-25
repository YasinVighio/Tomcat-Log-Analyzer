package org.tomcatlogwatcher.dataaccess;

import org.tomcatlogwatcher.core.PropManager;

public class DbUtil {
    public static String mapJavaTypeToH2Type(Class<?> javaType) {
        String varcharCol = PropManager.getCaseSensitiveColumn() ? "VARCHAR" : "VARCHAR_IGNORECASE";
        if (javaType == String.class) return varcharCol;
        if (javaType == Integer.class || javaType == int.class) return "INT";
        if (javaType == Long.class || javaType == long.class) return "BIGINT";
        if (javaType == Double.class || javaType == double.class) return "DOUBLE";
        if (javaType == Float.class || javaType == float.class) return "REAL";
        if (javaType == Boolean.class || javaType == boolean.class) return "BOOLEAN";
        if (javaType == java.sql.Timestamp.class || javaType == java.util.Date.class) return "TIMESTAMP";
        return varcharCol;
    }
}
