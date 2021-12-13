package com.management.erp.models.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "courseenrol")
public class CourseEnrolModel {

    @Id
    @JsonIgnore
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courseId", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private CourseModel courseId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "degreeId", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private DegreeModel degreeId;

    private int semester;
    private int enrolment;

    public CourseEnrolModel(CourseModel courseId, DegreeModel degreeId, int semester, int enrolment) {
        this.courseId = courseId;
        this.degreeId = degreeId;
        this.semester = semester;
        this.enrolment = enrolment;
    }

    public CourseEnrolModel() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CourseModel getCourseId() {
        return courseId;
    }

    public void setCourseId(CourseModel courseId) {
        this.courseId = courseId;
    }

    public DegreeModel getDegreeId() {
        return degreeId;
    }

    public void setDegreeId(DegreeModel degreeId) {
        this.degreeId = degreeId;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getEnrolment() {
        return enrolment;
    }

    public void setEnrolment(int enrolment) {
        this.enrolment = enrolment;
    }
}
