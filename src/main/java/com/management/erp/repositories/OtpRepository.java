package com.management.erp.repositories;

import com.management.erp.models.repository.OtpModel;
import com.management.erp.models.repository.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface OtpRepository extends JpaRepository<OtpModel, Long> {
    Optional<OtpModel> findByUserAndTimeAfter(UserModel userModel, LocalDateTime time);
    Optional<OtpModel> findByUser(UserModel userModel);
}
