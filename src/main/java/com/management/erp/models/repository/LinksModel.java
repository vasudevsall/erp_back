package com.management.erp.models.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "links")
public class LinksModel {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "announcement", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private CourseAnnouncementModel announcement;

    private String link;

    public LinksModel(long id, CourseAnnouncementModel announcement, String link) {
        this.id = id;
        this.announcement = announcement;
        this.link = link;
    }

    public LinksModel() {}

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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
