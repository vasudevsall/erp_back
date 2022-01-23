package com.management.erp.repositories;

import com.management.erp.models.repository.CourseModel;
import com.management.erp.models.repository.GradesModel;
import com.management.erp.models.repository.StudentModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradesRepository extends JpaRepository<GradesModel, Long> {
    List<GradesModel> findAllByStudent(StudentModel student);
    List<GradesModel> findAllByStudentAndCourse(StudentModel student, CourseModel course);
}
