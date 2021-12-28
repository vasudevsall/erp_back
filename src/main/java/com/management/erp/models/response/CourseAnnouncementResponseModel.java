package com.management.erp.models.response;

import com.management.erp.models.repository.CourseAnnouncementModel;
import com.management.erp.models.repository.FilesModel;
import com.management.erp.models.repository.LinksModel;

import java.util.List;

public class CourseAnnouncementResponseModel {

    private CourseAnnouncementModel announcement;
    private List<FilesModel> files;
    private List<LinksModel> links;

    public CourseAnnouncementResponseModel(CourseAnnouncementModel announcement, List<FilesModel> files, List<LinksModel> links) {
        this.announcement = announcement;
        this.files = files;
        this.links = links;
    }

    public CourseAnnouncementModel getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(CourseAnnouncementModel announcement) {
        this.announcement = announcement;
    }

    public List<FilesModel> getFiles() {
        return files;
    }

    public void setFiles(List<FilesModel> files) {
        this.files = files;
    }

    public List<LinksModel> getLinks() {
        return links;
    }

    public void setLinks(List<LinksModel> links) {
        this.links = links;
    }
}
