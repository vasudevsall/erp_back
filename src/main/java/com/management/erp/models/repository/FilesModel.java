package com.management.erp.models.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "files")
public class FilesModel {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "announcement", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private CourseAnnouncementModel announcement;

    private String file;

    public FilesModel(long id, CourseAnnouncementModel announcement, String file) {
        this.id = id;
        this.announcement = announcement;
        this.file = file;
    }

    public FilesModel() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @JsonIgnore
    public CourseAnnouncementModel getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(CourseAnnouncementModel announcement) {
        this.announcement = announcement;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
