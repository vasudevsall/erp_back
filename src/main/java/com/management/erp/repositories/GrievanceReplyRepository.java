package com.management.erp.repositories;

import com.management.erp.models.repository.GrievanceModel;
import com.management.erp.models.repository.GrievanceReplyModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GrievanceReplyRepository extends JpaRepository<GrievanceReplyModel, Long> {
    List<GrievanceReplyModel> findAllByGrievance(GrievanceModel grievanceModel);
}
