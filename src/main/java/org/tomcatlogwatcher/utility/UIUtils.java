package org.tomcatlogwatcher.utility;

import javax.swing.*;


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
}
