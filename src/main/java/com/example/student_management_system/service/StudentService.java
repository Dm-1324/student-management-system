package com.example.student_management_system.service;

import com.example.student_management_system.dto.StudentDetailsDto;
import com.example.student_management_system.dto.StudentDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {

    public StudentDto addStudent(StudentDto studentDto);

    public List<StudentDto> getAllStudents();

    public StudentDto getStudentById(Long id);

    public void updateStudentDetails(Long id, StudentDetailsDto studentDetailsDto);

    public void deleteStudent(Long id);
}
