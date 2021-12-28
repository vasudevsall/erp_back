package com.management.erp.repositories;

import com.management.erp.models.repository.CourseAnnouncementModel;
import com.management.erp.models.repository.LinksModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LinksRepository extends JpaRepository<LinksModel, Long> {
    List<LinksModel> findAllByAnnouncement(CourseAnnouncementModel announcement);
}
