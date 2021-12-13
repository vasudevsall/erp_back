package com.management.erp.repositories;

import com.management.erp.models.repository.AdminModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<AdminModel, String> {
}
