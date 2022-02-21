package com.management.erp.repositories;

import com.management.erp.models.repository.GrievanceModel;
import com.management.erp.models.repository.StudentModel;
import com.management.erp.models.repository.UserModel;
import com.management.erp.utils.GrievanceStatus;
import com.management.erp.utils.GrievanceTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GrievanceRepository extends JpaRepository<GrievanceModel, Long> {
    List<GrievanceModel> findAllByStudent(StudentModel student);
    List<GrievanceModel> findAllByDepartment(GrievanceTypeEnum department);
    List<GrievanceModel> findAllByForwardedTo(UserModel user);
    List<GrievanceModel> findAllByStatus(GrievanceStatus status);
}
