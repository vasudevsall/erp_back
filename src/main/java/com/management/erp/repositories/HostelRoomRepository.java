package com.management.erp.repositories;

import com.management.erp.models.repository.HostelModel;
import com.management.erp.models.repository.HostelRoomModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HostelRoomRepository extends JpaRepository<HostelRoomModel, Long> {
    List<HostelRoomModel> findAllByHostel(HostelModel hostel);
}
