package com.example.student_management_system.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum StudentStatus {
    ACTIVE,
    GRADUATED,
    SUSPENDED;

    @JsonCreator
    public static StudentStatus fromString(String value) {
        return StudentStatus.valueOf(value.toUpperCase());
    }
}