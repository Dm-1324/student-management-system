package com.example.student_management_system.service;

import com.example.student_management_system.dto.StudentDetailsDto;
import com.example.student_management_system.dto.StudentDto;
import com.example.student_management_system.entity.Student;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {

    public Student addStudent(StudentDto dto);

    public List<Student> getAllStudents();

    public Student getStudentById(Long id);

    public void updateStudentDetails(Long id, StudentDetailsDto dto);

    public void deleteStudent(Long id);
}
