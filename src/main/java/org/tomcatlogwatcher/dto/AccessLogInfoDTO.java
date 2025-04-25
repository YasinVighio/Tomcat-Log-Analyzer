package org.tomcatlogwatcher.dto;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AccessLogInfoDTO {
    private String apachePattern;
    private String description;
    private String dbColumnName;
    private Boolean isLongText;
    private Boolean isCustom;
    private Class<?> javaType;
    private String sqlType;
}
