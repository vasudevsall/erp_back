package com.management.erp.repositories;

import com.management.erp.models.repository.FacultyModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyRepository extends JpaRepository<FacultyModel, String> {
}
