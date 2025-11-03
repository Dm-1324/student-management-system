package com.example.student_management_system.dto;

import com.example.student_management_system.entity.StudentCourse;
import com.example.student_management_system.entity.StudentStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class StudentDetailsDto {
    private BigDecimal marks;
    private StudentCourse course;
    private StudentStatus status;
}
