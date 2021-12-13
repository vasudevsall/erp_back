package com.management.erp.repositories;

import com.management.erp.models.repository.EnrolmentModel;
import com.management.erp.models.repository.StudentModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrolmentRepository extends JpaRepository<EnrolmentModel, Long> {
    List<EnrolmentModel> findAllByStudent(StudentModel student);
}
