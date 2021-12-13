package com.management.erp.repositories;

import com.management.erp.models.repository.CourseModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<CourseModel, String> {
}
