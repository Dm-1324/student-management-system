package com.example.student_management_system.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StudentDetailsDto {
    private BigDecimal marks;
    private String course;
    private String status;
}
