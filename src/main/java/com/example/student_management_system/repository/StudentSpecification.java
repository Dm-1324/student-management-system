package com.example.student_management_system.repository;

import com.example.student_management_system.entity.Student;
import com.example.student_management_system.entity.enums.StudentCourse;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class StudentSpecification {
    public Specification<Student> filterStudents(
            String course,
            Integer year,
            BigDecimal minMarks,
            BigDecimal maxMarks,
            Boolean partialCourseMatch
    ) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            Optional.ofNullable(course)
                    .filter(StringUtils::hasText)
                    .ifPresent(s -> {
                        if (partialCourseMatch) {
                            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("course").as(String.class)), "%" + s + "%"));
                        } else {
                            try {
                                StudentCourse studentCourse = StudentCourse.fromString(s);
                                predicates.add(criteriaBuilder.equal(root.get("course"), studentCourse));
                            } catch (IllegalArgumentException e) {
                            }
                        }
                    });

            Optional.ofNullable(year)
                    .ifPresent(y -> predicates.add(criteriaBuilder.equal(root.get("year"), y)));

            Optional.ofNullable(minMarks)
                    .ifPresent(min -> predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("marks"), min)));

            Optional.ofNullable(maxMarks)
                    .ifPresent(max -> predicates.add(criteriaBuilder.lessThan(root.get("marks"), max)));

            query.distinct(true);

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });

    }
}
