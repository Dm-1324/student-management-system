package com.example.student_management_system.service;

import com.example.student_management_system.config.StudentManagementProperties;
import com.example.student_management_system.dto.StudentDetailsDto;
import com.example.student_management_system.dto.StudentDto;
import com.example.student_management_system.entity.Student;
import com.example.student_management_system.entity.enums.StudentStatus;
import com.example.student_management_system.exception.ResourceNotFoundException;
import com.example.student_management_system.mapper.StudentMapper;
import com.example.student_management_system.repository.StudentRepository;
import com.example.student_management_system.repository.StudentSpecification;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    private final StudentManagementProperties properties;
    private final StudentSpecification studentSpecification;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, StudentMapper studentMapper, StudentManagementProperties properties, StudentSpecification studentSpecification) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.properties = properties;
        this.studentSpecification = studentSpecification;
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
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));
        return studentMapper.toDto(student);
    }

    @Override
    public void updateStudentDetails(Long id, StudentDetailsDto studentDetailsDto) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));

        student.setCourse(nonNull(studentDetailsDto.getCourse()) ? studentDetailsDto.getCourse() : student.getCourse());
        student.setMarks(nonNull(studentDetailsDto.getMarks()) ? studentDetailsDto.getMarks() : student.getMarks());
        student.setStatus(nonNull(studentDetailsDto.getStatus()) ? studentDetailsDto.getStatus() : student.getStatus());

        studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));
        studentRepository.delete(student);

    }

    @Override
    public Page<StudentDto> getStudentsFiltered(String course, Integer year, BigDecimal minMarks, BigDecimal maxMarks, Pageable pageable) {
        Integer filterYear = year;


        if (course == null && year == null && minMarks == null && maxMarks == null) {
            filterYear = properties.getMaxYear();
        }

        Specification<Student> spec = studentSpecification.filterStudents(
                course,
                filterYear,
                minMarks,
                maxMarks,
                properties.getFilter().getPartialCourseMatch()
        );
        Page<Student> studentsPage = studentRepository.findAll(spec, pageable);

        return studentsPage.map(studentMapper::toDto);
    }

    @Override
    public Page<StudentDto> getFilteredStudents(String course, Integer year, BigDecimal minMarks, BigDecimal maxMarks, Pageable pageable) {

        Integer filterYear = year;


        if (course == null && year == null && minMarks == null && maxMarks == null) {
            filterYear = properties.getMaxYear();
        }


        Boolean partialMatchConfig = properties.getFilter().getPartialCourseMatch();

        int defaultPageSize = properties.getFilter().getPageSize();
        Pageable newPageable = pageable;
        if (pageable.getPageSize() == 10) {
            newPageable = PageRequest.of(pageable.getPageNumber(),
                    defaultPageSize,
                    pageable.getSort());
        }

        Page<Student> studentPage = studentRepository.findOrdersFilteredNative(
                course,
                filterYear,
                minMarks,
                maxMarks,
                partialMatchConfig,
                newPageable
        );

        return studentPage.map(studentMapper::toDto);
    }

    @Override
    public void promoteStudent(Long id, Integer promoteToYear) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));

        if (student.getStatus() == StudentStatus.GRADUATED) {
            throw new RuntimeException("Promotion denied: Student has already graduated.");
        }

        BigDecimal minMarks = properties.getPromotion().getMinMarks();
        Boolean allowSkip = properties.getPromotion().getAllowSkip();
        Integer maxYear = properties.getMaxYear();
        Integer currentYear = student.getYear();

        if (student.getMarks().compareTo(minMarks) < 0) {
            throw new RuntimeException("Promotion not allowed, marks doesn't meet the minimum requirement");
        }
        if (promoteToYear <= currentYear) {
            throw new RuntimeException("Promotion not allowed, can't promote to a year less than or equal to current " +
                    "year");
        }
        if (!allowSkip && promoteToYear > currentYear + 1) {
            throw new RuntimeException("Promotion not allowed, can't skip a year");
        }
        if (promoteToYear > maxYear) {
            student.setStatus(StudentStatus.GRADUATED);
            student.setYear(maxYear);
        } else {
            student.setYear(promoteToYear);
            student.setStatus(StudentStatus.ACTIVE);
        }

        studentRepository.save(student);
    }

    @Override
    @Transactional
    public List<StudentDto> bulkAddStudents(List<StudentDto> studentDtos) {
        List<Student> students = studentDtos.stream().map(studentMapper::toEntity).toList();

        List<Student> savedStudent = studentRepository.saveAll(students);

        return savedStudent.stream().map(studentMapper::toDto).toList();
    }

    @Override
    public void bulkDeleteStudents(List<Long> studentIds) {
        studentRepository.deleteAllById(studentIds);
    }
}
