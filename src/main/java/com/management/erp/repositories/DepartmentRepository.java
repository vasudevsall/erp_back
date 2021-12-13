package com.management.erp.repositories;

import com.management.erp.models.repository.DepartmentModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<DepartmentModel, String> {
}
