package com.example.student_management_system.repository;

import com.example.student_management_system.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {

    @Query(
            value = """
                    SELECT * FROM students s
                    WHERE
                        (:course IS NULL OR
                            (CAST(:partialCourseMatch AS UNSIGNED) = 1 AND LOWER(s.course) LIKE CONCAT('%', LOWER(:course), '%'))
                            OR
                            (CAST(:partialCourseMatch AS UNSIGNED) = 0 AND s.course = UPPER(:course))
                        )
                        AND (:year IS NULL OR s.year = :year)
                        AND (:minMarks IS NULL OR s.marks >= :minMarks)
                        AND (:maxMarks IS NULL OR s.marks <= :maxMarks)
                    """,
            countQuery = """
                    SELECT COUNT(*) FROM students s
                    WHERE
                        (:course IS NULL OR
                            (CAST(:partialCourseMatch AS UNSIGNED) = 1 AND LOWER(s.course) LIKE CONCAT('%', LOWER(:course), '%'))
                            OR
                            (CAST(:partialCourseMatch AS UNSIGNED) = 0 AND s.course = UPPER(:course))
                        )
                        AND (:year IS NULL OR s.year = :year)
                        AND (:minMarks IS NULL OR s.marks >= :minMarks)
                        AND (:maxMarks IS NULL OR s.marks <= :maxMarks)
                    """,
            nativeQuery = true
    )
    Page<Student> findOrdersFilteredNative(
            @Param("course") String course,
            @Param("year") Integer year,
            @Param("minMarks") BigDecimal minMarks,
            @Param("maxMarks") BigDecimal maxMarks,
            @Param("partialCourseMatch") Boolean partialCourseMatch,
            Pageable pageable
    );

}
