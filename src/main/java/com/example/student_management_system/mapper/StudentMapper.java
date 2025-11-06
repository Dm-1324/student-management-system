package com.example.student_management_system.mapper;

import com.example.student_management_system.dto.StudentDto;
import com.example.student_management_system.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {
    public Student toEntity(StudentDto dto) {
        return Student.builder()
                .name(dto.getName())
                .marks(dto.getMarks())
                .year(dto.getYear())
                .course(dto.getCourse())
                .rollNumber(dto.getRollNumber())
                .status(dto.getStatus())
                .build();
    }


}