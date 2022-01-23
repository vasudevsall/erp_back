package com.management.erp.models.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "grades")
public class GradesModel {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    @JsonIgnore
    private StudentModel student;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private CourseModel course;
    private int semester;

    @Column(name = "minor_one")
    private double minorOne;
    @Column(name = "minor_two")
    private double minorTwo;
    private double major;

    @Column(name = "lab_file")
    private double labFile;
    @Column(name = "lab_viva")
    private double labViva;
    private double lab;

    private double tute;

    private double total;
    private String grade;

    public GradesModel(
        StudentModel student, CourseModel course,
        int semester, double minorOne, double minorTwo, double major,
        double labFile, double labViva, double lab, double tute,
        double total, String grade
    ) {
        this.student = student;
        this.course = course;
        this.semester = semester;
        this.minorOne = minorOne;
        this.minorTwo = minorTwo;
        this.major = major;
        this.labFile = labFile;
        this.labViva = labViva;
        this.lab = lab;
        this.tute = tute;
        this.total = total;
        this.grade = grade;
    }

    public GradesModel() {}

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

    public CourseModel getCourse() {
        return course;
    }

    public void setCourse(CourseModel course) {
        this.course = course;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public double getMinorOne() {
        return minorOne;
    }

    public void setMinorOne(double minorOne) {
        this.minorOne = minorOne;
    }

    public double getMinorTwo() {
        return minorTwo;
    }

    public void setMinorTwo(double minorTwo) {
        this.minorTwo = minorTwo;
    }

    public double getMajor() {
        return major;
    }

    public void setMajor(double major) {
        this.major = major;
    }

    public double getLabFile() {
        return labFile;
    }

    public void setLabFile(double labFile) {
        this.labFile = labFile;
    }

    public double getLabViva() {
        return labViva;
    }

    public void setLabViva(double labViva) {
        this.labViva = labViva;
    }

    public double getLab() {
        return lab;
    }

    public void setLab(double lab) {
        this.lab = lab;
    }

    public double getTute() {
        return tute;
    }

    public void setTute(double tute) {
        this.tute = tute;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
