package com.example.student_management_system.dto;


import com.example.student_management_system.entity.StudentCourse;
import com.example.student_management_system.entity.StudentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class StudentDto {

    @NotBlank(message = "Name is required.")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Name must only contain letters and spaces.")
    @Schema(example = "string")
    private String name;

    @NotBlank(message = "Roll number is required.")
    private String rollNumber;

    @NotNull(message = "Course is required")
    private StudentCourse course;

    @NotNull(message = "Year is required.")
    @Min(value = 1, message = "Year must be 1 or greater.")
    @Max(value = 4, message = "Year cannot exceed 4.")
    private Integer year;

    @NotNull(message = "Marks are required.")
    @DecimalMin(value = "0.0", message = "Marks cannot be negative.")
    @Max(value = 100, message = "Marks cannot exceed 100.")
    @Digits(integer = 3, fraction = 2, message = "Marks must have at most 2 decimal places.")
    private BigDecimal marks;
    @NotNull(message = "Status is required")
    private StudentStatus status;
}