package com.management.erp.models.custom;

import com.management.erp.models.repository.CourseModel;

import java.util.List;

public class CourseResponseModel {

    private CourseModel course;
    private List<CourseAnnouncementResponseModel> announcements;

    public CourseResponseModel(CourseModel course, List<CourseAnnouncementResponseModel> announcements) {
        this.course = course;
        this.announcements = announcements;
    }

    public CourseModel getCourse() {
        return course;
    }

    public void setCourse(CourseModel course) {
        this.course = course;
    }

    public List<CourseAnnouncementResponseModel> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(List<CourseAnnouncementResponseModel> announcements) {
        this.announcements = announcements;
    }
}
