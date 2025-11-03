package com.example.student_management_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "roll_number", nullable = false, length = 20, unique = true)
    private String rollNumber;

    @Column(length = 50)
    private String course;

    private Integer year;

    private BigDecimal marks;

    @Column(length = 20)
    private String status;


    public Student(String name, String rollNumber, String course, Integer year, BigDecimal marks, String status) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.course = course;
        this.year = year;
        this.marks = marks;
        this.status = status;
    }
}