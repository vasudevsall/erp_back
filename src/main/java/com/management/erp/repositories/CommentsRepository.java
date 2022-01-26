package com.management.erp.repositories;

import com.management.erp.models.repository.CommentsModel;
import com.management.erp.models.repository.CourseAnnouncementModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository extends JpaRepository<CommentsModel, Long> {
    List<CommentsModel> findAllByAnnouncement(CourseAnnouncementModel announcement);
}
