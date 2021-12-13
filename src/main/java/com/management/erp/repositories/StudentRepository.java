package com.management.erp.repositories;

import com.management.erp.models.repository.StudentModel;
import com.management.erp.models.repository.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<StudentModel, String> {
    Optional<StudentModel> findByUserId(UserModel userId);
}
