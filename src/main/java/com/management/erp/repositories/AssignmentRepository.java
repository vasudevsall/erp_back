package com.management.erp.repositories;

import com.management.erp.models.repository.AssignmentModel;
import com.management.erp.models.repository.CourseModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AssignmentRepository extends JpaRepository<AssignmentModel, Long> {
    List<AssignmentModel> findAllByCourse(CourseModel course);
    List<AssignmentModel> findAllByCourseAndDueGreaterThanEqual(CourseModel course, LocalDate date);
}
