package com.management.erp.repositories;

import com.management.erp.models.repository.CourseAnnouncementModel;
import com.management.erp.models.repository.FilesModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilesRepository extends JpaRepository<FilesModel, Long> {
    List<FilesModel> findAllByAnnouncement(CourseAnnouncementModel announcement);
}
