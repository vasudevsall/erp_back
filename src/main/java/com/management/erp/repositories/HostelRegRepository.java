package com.management.erp.repositories;

import com.management.erp.models.repository.HostelRegModel;
import com.management.erp.models.repository.HostelRoomModel;
import com.management.erp.models.repository.StudentModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HostelRegRepository extends JpaRepository<HostelRegModel, Long> {
    Optional<HostelRegModel> findByStudent(StudentModel student);
    List<HostelRegModel> findAllByRoom(HostelRoomModel hostelRoomModel);
}
