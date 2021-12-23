package com.management.erp.repositories;

import com.management.erp.models.repository.CourseModel;
import com.management.erp.models.repository.FacultyModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<CourseModel, String> {
    List<CourseModel> findAllByFaculty(FacultyModel faculty);
}
