package com.example.student_management_system.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.math.BigDecimal;

@ConfigurationProperties(prefix = "student")
@Data
public class StudentManagementProperties {

    private FilterProperties filter = new FilterProperties();
    private PromotionProperties promotion = new PromotionProperties();
    private Integer maxYear;


    @Data
    public static class FilterProperties {
        private Boolean partialCourseMatch = true;
    }

    @Data
    public static class PromotionProperties {
        private BigDecimal minMarks = BigDecimal.valueOf(40.0);
        private Boolean allowSkip = false;
    }
}
