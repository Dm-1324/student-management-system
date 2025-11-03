package com.example.student_management_system.mapper;

import com.example.student_management_system.dto.StudentDto;
import com.example.student_management_system.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {
    public Student toEntity(StudentDto dto) {
        return new Student(
                dto.getName(),
                dto.getRollNumber(),
                dto.getCourse(),
                dto.getYear(),
                dto.getMarks(),
                dto.getStatus()
        );
    }


}