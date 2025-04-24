package org.tomcatlogwatcher.data;

public interface Constants {

    String DEFAULT_PATTERN = "%h %l %u %t \"%r\" %s %b";

    enum STRING_SEPARATOR {
        DOT("."),
        PATH_SEPARATOR_FORWARD_SLASH("/"),
        PATH_SEPARATOR_BACK_SLASH("\\"),
        PATH_SEPARATOR_BACK_SLASH_ESCAPE("\\\\"),
        SPACE(" "),
        SQUARE_BRACKET_OPEN("["),
        SQUARE_BRACKET_CLOSE("]"),
        QUOTATION("\""),
        DASH("-"),
        COMMA(",");

        private final String value;

        STRING_SEPARATOR(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    enum DATE_FORMATS{
        dd_MM_YYYY("dd-MM-yyyy"),
        dd_MMM_YYYY("dd-MMM-yyyy"),
        HH_mm_ss("HH:mm:ss"),
        dd_MM_YYYY_HH_mm_ss("dd-MM-yyyy HH:mm:ss"),
        COMMON_LOGGING_FORMAT("dd/MMM/yyyy:HH:mm:ss Z"),
        COMMON_LOGGING_FORMAT_WITHOUT_ZONE("dd/MMM/yyyy:HH:mm:ss"),
        COMMON_LOGGING_FORMAT_WITH_MS_WITHOUT_ZONE("dd/MMM/yyyy:HH:mm:ss.SSS"),
        COMMON_LOGGING_FORMAT_WITH_MS("dd/MMM/yyyy:HH:mm:ss.SSS Z");

        private final String value;

        DATE_FORMATS(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    enum TIME_ZONE_OFFSET {
        POSITIVE("+"),
        NEGATIVE("-");

        private final String value;

        TIME_ZONE_OFFSET(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
