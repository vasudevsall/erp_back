package com.management.erp.models.repository;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "grievance_reply")
public class GrievanceReplyModel {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "grievance", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private GrievanceModel grievance;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sender", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private UserModel sender;

    private LocalDateTime datetime;

    public GrievanceReplyModel(GrievanceModel grievance, UserModel sender, LocalDateTime datetime) {
        this.grievance = grievance;
        this.sender = sender;
        this.datetime = datetime;
    }

    public GrievanceReplyModel() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public GrievanceModel getGrievance() {
        return grievance;
    }

    public void setGrievance(GrievanceModel grievance) {
        this.grievance = grievance;
    }

    public UserModel getSender() {
        return sender;
    }

    public void setSender(UserModel sender) {
        this.sender = sender;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }
}
