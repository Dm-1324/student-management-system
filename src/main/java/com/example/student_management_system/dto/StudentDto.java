package com.example.student_management_system.dto;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class StudentDto {

    @NotBlank(message = "Name is required.")
    private String name;

    @NotBlank(message = "Roll number is required.")
    private String rollNumber;

    private String course;

    @NotNull(message = "Year is required.")
    @Min(value = 1, message = "Year must be 1 or greater.")
    @Max(value = 4, message = "Year cannot exceed 4.")
    private Integer year;

    @NotNull(message = "Marks are required.")
    @DecimalMin(value = "0.0", message = "Marks cannot be negative.")
    private BigDecimal marks;

    private String status;
}