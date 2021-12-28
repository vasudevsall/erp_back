package com.management.erp.models.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "course_announcement")
public class CourseAnnouncementModel {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private CourseModel courseId;

    private String title;
    private String announce;
    private LocalDateTime time;

    public CourseAnnouncementModel(CourseModel courseId, String title, String announce, LocalDateTime time) {
        this.courseId = courseId;
        this.title = title;
        this.announce = announce;
        this.time = time;
    }

    public CourseAnnouncementModel() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @JsonIgnore
    public CourseModel getCourseId() {
        return courseId;
    }

    public void setCourseId(CourseModel courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnnounce() {
        return announce;
    }

    public void setAnnounce(String announce) {
        this.announce = announce;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
