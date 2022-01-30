package com.management.erp.repositories;

import com.management.erp.models.repository.AssignmentModel;
import com.management.erp.models.repository.AssignmentSubmitModel;
import com.management.erp.models.repository.StudentModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssignmentSubmitRepository extends JpaRepository<AssignmentSubmitModel, Long> {
    List<AssignmentSubmitModel> findAllByAssignment(AssignmentModel assignment);
    Optional<AssignmentSubmitModel> findByAssignmentAndStudent(AssignmentModel assignment, StudentModel student);
}
