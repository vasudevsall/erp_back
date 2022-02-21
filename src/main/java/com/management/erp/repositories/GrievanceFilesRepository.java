package com.management.erp.repositories;

import com.management.erp.models.repository.GrievanceFilesModel;
import com.management.erp.models.repository.GrievanceModel;
import com.management.erp.models.repository.GrievanceReplyModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GrievanceFilesRepository extends JpaRepository<GrievanceFilesModel, Long> {
    List<GrievanceFilesModel> findAllByGrievance(GrievanceModel grievanceModel);
    List<GrievanceFilesModel> findAllByReply(GrievanceReplyModel grievanceReplyModel);
}
