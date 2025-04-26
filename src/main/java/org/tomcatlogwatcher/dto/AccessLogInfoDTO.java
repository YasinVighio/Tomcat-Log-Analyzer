package org.tomcatlogwatcher.dto;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AccessLogInfoDTO {
    private String apachePattern;
    private String description;
    private String dbColumnName;

    private String sqlType;
    private Class<?> javaType;

    private boolean isLongText;
    private boolean isCustom;
}
