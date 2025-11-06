package com.example.student_management_system.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Name must only contain letters and spaces.")
    @Schema(example = "string")
    private String name;

    @Column(name = "roll_number", nullable = false, length = 20, unique = true)
    private String rollNumber;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private StudentCourse course;

    @NotNull(message = "Year is required.")
    @Min(value = 1, message = "Year must be 1 or greater.")
    @Max(value = 4, message = "Year cannot exceed 4.")
    private Integer year;

    @Max(value = 100, message = "Marks cannot exceed 100.")
    @Digits(integer = 3, fraction = 2, message = "Marks must have at most 2 decimal places.")
    private BigDecimal marks;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private StudentStatus status;


    public Student(String name, String rollNumber, StudentCourse course, Integer year, BigDecimal marks, StudentStatus status) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.course = course;
        this.year = year;
        this.marks = marks;
        this.status = status;
    }
}