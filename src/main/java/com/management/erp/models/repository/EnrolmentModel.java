package com.management.erp.models.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "enrolment")
public class EnrolmentModel {

    @Id
    @JsonIgnore
    private long id;

    private long enrolNo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private StudentModel student;

    public EnrolmentModel(long enrolNo, StudentModel student) {
        this.enrolNo = enrolNo;
        this.student = student;
    }

    public EnrolmentModel(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEnrolNo() {
        return enrolNo;
    }

    public void setEnrolNo(long enrolNo) {
        this.enrolNo = enrolNo;
    }

    public StudentModel getStudent() {
        return student;
    }

    public void setStudent(StudentModel student) {
        this.student = student;
    }
}
