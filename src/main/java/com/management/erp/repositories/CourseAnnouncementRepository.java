package com.management.erp.repositories;

import com.management.erp.models.repository.CourseAnnouncementModel;
import com.management.erp.models.repository.CourseModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CourseAnnouncementRepository extends JpaRepository<CourseAnnouncementModel, Long> {
    List<CourseAnnouncementModel> findAllByCourseId(CourseModel courseId);
    List<CourseAnnouncementModel> findAllByCourseIdAndTimeBefore(CourseModel courseId, LocalDateTime localDateTime);
    List<CourseAnnouncementModel> findAllByCourseIdAndTimeAfter(CourseModel courseId, LocalDateTime localDateTime);
    List<CourseAnnouncementModel> findAllByCourseIdAndTimeBetween(
            CourseModel courseId, LocalDateTime startTime, LocalDateTime endTime
    );
}
