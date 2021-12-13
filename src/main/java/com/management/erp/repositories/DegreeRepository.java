package com.management.erp.repositories;

import com.management.erp.models.repository.DegreeModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DegreeRepository extends JpaRepository<DegreeModel, Integer> {
}
