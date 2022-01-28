package com.management.erp.models.repository;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class CommentsModel {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "announcement", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private CourseAnnouncementModel announcement;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private UserModel user;

    private String comment;
    private LocalDateTime time;

    public CommentsModel(
        CourseAnnouncementModel announcement, UserModel user,
        String comment, LocalDateTime time
    ) {
        this.announcement = announcement;
        this.user = user;
        this.comment = comment;
        this.time = time;
    }

    public CommentsModel() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public CourseAnnouncementModel getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(CourseAnnouncementModel announcement) {
        this.announcement = announcement;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
