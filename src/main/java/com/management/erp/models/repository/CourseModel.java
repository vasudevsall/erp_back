package com.management.erp.models.repository;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "course")
public class CourseModel {

    @Id
    private String id;

    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private DepartmentModel departmentId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private FacultyModel faculty;

    private int credits;
    private double theory;
    private double practical;
    private double tutorial;
    private int students;
    private char type;
    private String syllabus;

    public CourseModel(String id, String name, DepartmentModel departmentId, FacultyModel faculty, int credits, double theory, double practical, double tutorial, int students, char type, String syllabus) {
        this.id = id;
        this.name = name;
        this.departmentId = departmentId;
        this.faculty = faculty;
        this.credits = credits;
        this.theory = theory;
        this.practical = practical;
        this.tutorial = tutorial;
        this.students = students;
        this.type = type;
        this.syllabus = syllabus;
    }

    public CourseModel(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DepartmentModel getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(DepartmentModel departmentId) {
        this.departmentId = departmentId;
    }

    public FacultyModel getFaculty() {
        return faculty;
    }

    public void setFaculty(FacultyModel faculty) {
        this.faculty = faculty;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public double getTheory() {
        return theory;
    }

    public void setTheory(double theory) {
        this.theory = theory;
    }

    public double getPractical() {
        return practical;
    }

    public void setPractical(double practical) {
        this.practical = practical;
    }

    public double getTutorial() {
        return tutorial;
    }

    public void setTutorial(double tutorial) {
        this.tutorial = tutorial;
    }

    public int getStudents() {
        return students;
    }

    public void setStudents(int students) {
        this.students = students;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public String getSyllabus() {
        return syllabus;
    }

    public void setSyllabus(String syllabus) {
        this.syllabus = syllabus;
    }
}
