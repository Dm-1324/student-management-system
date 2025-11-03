package com.example.student_management_system.dto;

import com.example.student_management_system.entity.StudentCourse;
import com.example.student_management_system.entity.StudentStatus;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class StudentDetailsDto {

    @Max(value = 100, message = "Marks cannot exceed 100.")
    @Digits(integer = 3, fraction = 2, message = "Marks must have at most 2 decimal places.")
    private BigDecimal marks;
    private StudentCourse course;
    private StudentStatus status;
}
