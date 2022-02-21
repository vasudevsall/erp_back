package com.management.erp.models.repository;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.management.erp.utils.GrievanceStatus;
import com.management.erp.utils.GrievanceTypeEnum;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "grievance")
public class GrievanceModel {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private StudentModel student;

    @Enumerated(EnumType.STRING)
    private GrievanceTypeEnum department;

    private String title;
    private String description;

    private LocalDateTime datetime;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "forwarded_to", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private UserModel forwardedTo;

    @Enumerated(EnumType.STRING)
    private GrievanceStatus status;

    public GrievanceModel(StudentModel student, GrievanceTypeEnum department, String title, String description, LocalDateTime datetime, UserModel forwardedTo, GrievanceStatus status) {
        this.student = student;
        this.department = department;
        this.title = title;
        this.description = description;
        this.datetime = datetime;
        this.forwardedTo = forwardedTo;
        this.status = status;
    }

    public GrievanceModel() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public StudentModel getStudent() {
        return student;
    }

    public void setStudent(StudentModel student) {
        this.student = student;
    }

    public GrievanceTypeEnum getDepartment() {
        return department;
    }

    public void setDepartment(GrievanceTypeEnum department) {
        this.department = department;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public UserModel getForwardedTo() {
        return forwardedTo;
    }

    public void setForwardedTo(UserModel forwardedTo) {
        this.forwardedTo = forwardedTo;
    }

    public GrievanceStatus getStatus() {
        return status;
    }

    public void setStatus(GrievanceStatus status) {
        this.status = status;
    }
}
