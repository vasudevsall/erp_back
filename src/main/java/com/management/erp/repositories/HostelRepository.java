package com.management.erp.repositories;

import com.management.erp.models.repository.FacultyModel;
import com.management.erp.models.repository.HostelModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface HostelRepository extends JpaRepository<HostelModel, Long> {
    Optional<HostelModel> findByWarden(FacultyModel warden);
}
