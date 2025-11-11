package com.example.student_management_system.service;

import com.example.student_management_system.dto.StudentDetailsDto;
import com.example.student_management_system.dto.StudentDto;
import com.example.student_management_system.entity.Student;
import com.example.student_management_system.mapper.StudentMapper;
import com.example.student_management_system.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }


    @Override
    public StudentDto addStudent(StudentDto studentDto) {
        Student student = studentMapper.toEntity(studentDto);
        Student savedStudent = studentRepository.save(student);
        return studentMapper.toDto(savedStudent);
    }

    @Override
    public List<StudentDto> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(entity -> studentMapper.toDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public StudentDto getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id " + id));
        return studentMapper.toDto(student);
    }

    @Override
    public void updateStudentDetails(Long id, StudentDetailsDto studentDetailsDto) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No Student Found with ID " + id));

        student.setCourse(nonNull(studentDetailsDto.getCourse()) ? studentDetailsDto.getCourse() : student.getCourse());
        student.setMarks(nonNull(studentDetailsDto.getMarks()) ? studentDetailsDto.getMarks() : student.getMarks());
        student.setStatus(nonNull(studentDetailsDto.getStatus()) ? studentDetailsDto.getStatus() : student.getStatus());

        studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No Student Found with ID  " + id));
        studentRepository.delete(student);

    }
}
