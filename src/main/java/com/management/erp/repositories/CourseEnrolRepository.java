package com.management.erp.repositories;

import com.management.erp.models.repository.CourseEnrolModel;
import com.management.erp.models.repository.DegreeModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseEnrolRepository extends JpaRepository<CourseEnrolModel, Integer> {
    List<CourseEnrolModel> findAllByDegreeIdAndSemester(DegreeModel degreeId, int semester);
    Optional<CourseEnrolModel> findByEnrolment(int enrolment);
}
