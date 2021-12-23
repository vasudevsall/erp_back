package com.management.erp.repositories;

import com.management.erp.models.repository.FacultyModel;
import com.management.erp.models.repository.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FacultyRepository extends JpaRepository<FacultyModel, String> {
    Optional<FacultyModel> findByUserId(UserModel userId);
}
