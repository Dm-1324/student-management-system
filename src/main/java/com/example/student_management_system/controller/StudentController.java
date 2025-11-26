package com.example.student_management_system.controller;


import com.example.student_management_system.dto.PaginatedResponse;
import com.example.student_management_system.dto.PromoteStudentRequest;
import com.example.student_management_system.dto.StudentDetailsDto;
import com.example.student_management_system.dto.StudentDto;
import com.example.student_management_system.service.StudentServiceImpl;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentServiceImpl studentService;

    @Autowired
    public StudentController(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<StudentDto> addStudent(@Valid @RequestBody StudentDto studentDto) {
        StudentDto studentResponseDto = studentService.addStudent(studentDto);
        return new ResponseEntity<>(studentResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public List<StudentDto> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable Long id) {
        try {
            StudentDto studentResponseDto = studentService.getStudentById(id);
            return ResponseEntity.ok(studentResponseDto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //filter and paging
    @GetMapping("/filteredData")
    public ResponseEntity<PaginatedResponse<StudentDto>> getStudentsFiltered(
            @RequestParam(required = false) String course,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) BigDecimal minMarks,
            @RequestParam(required = false) BigDecimal maxMarks,
            @ParameterObject @PageableDefault(size = 15, sort = "marks", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<StudentDto> studentsPage = studentService.getStudentsFiltered(course, year, minMarks, maxMarks, pageable);

        PaginatedResponse<StudentDto> response = new PaginatedResponse<>(studentsPage);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/filteredRepo")
    public ResponseEntity<PaginatedResponse<StudentDto>> getFilteredStudents(
            @RequestParam(required = false) String course,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) BigDecimal minMarks,
            @RequestParam(required = false) BigDecimal maxMarks,
            @ParameterObject @PageableDefault(size = 15, sort = "marks", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<StudentDto> students = studentService.getStudentsFiltered(course, year, minMarks, maxMarks, pageable);
        PaginatedResponse<StudentDto> response = new PaginatedResponse<>(students);
        return ResponseEntity.ok(response);
    }

    //promotion
    @PatchMapping("/{id}/promote")
    public ResponseEntity<String> promoteStudent(
            @PathVariable Long id,
            @Valid @RequestBody PromoteStudentRequest request
    ) {
        try {
            studentService.promoteStudent(id, request.getPromoteToYear());
            return ResponseEntity.ok("Student Promoted Successfully");
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateStudent(@PathVariable Long id, @RequestBody StudentDetailsDto studentDetailsDto) {
        try {
            studentService.updateStudentDetails(id, studentDetailsDto);
            return ResponseEntity.ok("Student details updated successfully!");
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        try {
            studentService.deleteStudent(id);
            return ResponseEntity.ok("Student deleted successfully!");
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


}