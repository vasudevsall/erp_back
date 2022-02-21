package com.management.erp.models.repository;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "grievance_files")
public class GrievanceFilesModel {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private String file;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "grievance", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private GrievanceModel grievance;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "grievance_reply", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private GrievanceReplyModel reply;

    public GrievanceFilesModel(String file, GrievanceModel grievance, GrievanceReplyModel reply) {
        this.file = file;
        this.grievance = grievance;
        this.reply = reply;
    }

    public GrievanceFilesModel() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public GrievanceModel getGrievance() {
        return grievance;
    }

    public void setGrievance(GrievanceModel grievance) {
        this.grievance = grievance;
    }

    public GrievanceReplyModel getReply() {
        return reply;
    }

    public void setReply(GrievanceReplyModel reply) {
        this.reply = reply;
    }
}
