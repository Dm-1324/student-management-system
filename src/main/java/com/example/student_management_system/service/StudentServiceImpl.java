package com.example.student_management_system.service;

import com.example.student_management_system.dto.StudentDetailsDto;
import com.example.student_management_system.dto.StudentDto;
import com.example.student_management_system.entity.Student;
import com.example.student_management_system.mapper.StudentMapper;
import com.example.student_management_system.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Student addStudent(StudentDto dto) {
        Student student = studentMapper.toEntity(dto);
        return studentRepository.save(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id " + id));
    }

    @Override
    public void updateStudentDetails(Long id, StudentDetailsDto dto) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No Student Found with ID " + id));

        student.setCourse(nonNull(dto.getCourse()) ? dto.getCourse() : student.getCourse());
        student.setMarks(nonNull(dto.getMarks()) ? dto.getMarks() : student.getMarks());
        student.setStatus(nonNull(dto.getStatus()) ? dto.getStatus() : student.getStatus());

        studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No Student Found with ID + " + id));
        studentRepository.delete(student);

    }
}
