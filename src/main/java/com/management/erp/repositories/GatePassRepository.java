package com.management.erp.repositories;

import com.management.erp.models.repository.GatePassModel;
import com.management.erp.models.repository.HostelRegModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface GatePassRepository extends JpaRepository<GatePassModel, Long> {
    List<GatePassModel> findAllByHostelReg(HostelRegModel hostelRegModel);
    List<GatePassModel> findAllByHostelRegAndDateGreaterThanEqual(HostelRegModel hostelRegModel, LocalDate date);
    List<GatePassModel> findAllByHostelRegAndPermission(HostelRegModel hostelRegModel, boolean permission);
    List<GatePassModel> findAllByHostelRegAndSignedOnIsNull(HostelRegModel hostelRegModel);
    List<GatePassModel> findAllByHostelRegAndSignedOnIsNotNull(HostelRegModel hostelRegModel);
    List<GatePassModel> findAllByDate(LocalDate date);
}
