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

    public StudentDto toDto(Student entity) {
        StudentDto dto = new StudentDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setMarks(entity.getMarks());
        dto.setYear(entity.getYear());
        dto.setCourse(entity.getCourse());
        dto.setRollNumber(entity.getRollNumber());
        dto.setStatus(entity.getStatus());
        return dto;
    }


}