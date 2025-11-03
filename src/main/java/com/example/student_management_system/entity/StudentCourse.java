package com.example.student_management_system.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum StudentCourse {
    CS,
    EE,
    ME,
    IT;

    @JsonCreator
    public static StudentCourse fromString(String value) {
        return StudentCourse.valueOf(value.toUpperCase());
    }
}
