package com.example.student_management_system.service;

import com.example.student_management_system.dto.StudentDetailsDto;
import com.example.student_management_system.dto.StudentDto;
import com.example.student_management_system.entity.Student;
import com.example.student_management_system.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student addStudent(StudentDto dto) {
        Student student = new Student(dto.getName(), dto.getRollNumber(), dto.getCourse(), dto.getYear(), dto.getMarks(), dto.getStatus());
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
                .orElseThrow(() -> new RuntimeException("No Student Found with ID + " + id));


        if (dto.getCourse() != null) {
            student.setCourse(dto.getCourse());
        }
        if (dto.getMarks() != null) {
            student.setMarks(dto.getMarks());
        }
        if (dto.getStatus() != null) {
            student.setStatus(dto.getStatus());
        }

        studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No Student Found with ID + " + id));
        studentRepository.delete(student);

    }
}
