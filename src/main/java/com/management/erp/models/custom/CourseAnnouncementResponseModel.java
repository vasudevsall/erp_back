package com.management.erp.models.custom;

import com.management.erp.models.repository.CommentsModel;
import com.management.erp.models.repository.CourseAnnouncementModel;
import com.management.erp.models.repository.FilesModel;
import com.management.erp.models.repository.LinksModel;

import java.util.List;

public class CourseAnnouncementResponseModel {

    private CourseAnnouncementModel announcement;
    private List<FilesModel> files;
    private List<LinksModel> links;
    private List<CommentsModel> comments;

    public CourseAnnouncementResponseModel(
        CourseAnnouncementModel announcement, List<FilesModel> files,
        List<LinksModel> links, List<CommentsModel> comments
    ) {
        this.announcement = announcement;
        this.files = files;
        this.links = links;
        this.comments = comments;
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

    public List<CommentsModel> getComments() {
        return comments;
    }

    public void setComments(List<CommentsModel> comments) {
        this.comments = comments;
    }
}
