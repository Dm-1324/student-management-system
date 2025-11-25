package com.example.student_management_system.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PromoteStudentRequest {
    @NotNull(message = "Year cannot be empty")
    @Min(value = 1, message = "Year should be 1 or greater than 1")
    private Integer promoteToYear;
}
