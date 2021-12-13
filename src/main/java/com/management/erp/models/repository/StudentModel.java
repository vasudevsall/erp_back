package com.management.erp.models.repository;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "student")
public class StudentModel {

    @Id
    private String id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private UserModel userId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departmentId", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private DepartmentModel departmentId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "degree", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private DegreeModel degree;

    private String address;
    private String city;
    private String state;
    private String country;
    private String pincode;
    private int semester;
    private LocalDate admissionDate;
    private double cgpa;

    public StudentModel(String id, UserModel userId, DepartmentModel departmentId, DegreeModel degree, String address, String city, String state, String country, String pincode, int semester, LocalDate admissionDate, double cgpa) {
        this.id = id;
        this.userId = userId;
        this.departmentId = departmentId;
        this.degree = degree;
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.pincode = pincode;
        this.semester = semester;
        this.admissionDate = admissionDate;
        this.cgpa = cgpa;
    }

    public StudentModel() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserModel getUserId() {
        return userId;
    }

    public void setUserId(UserModel userId) {
        this.userId = userId;
    }

    public DepartmentModel getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(DepartmentModel departmentId) {
        this.departmentId = departmentId;
    }

    public DegreeModel getDegree() {
        return degree;
    }

    public void setDegree(DegreeModel degree) {
        this.degree = degree;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public LocalDate getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(LocalDate admissionDate) {
        this.admissionDate = admissionDate;
    }

    public double getCgpa() {
        return cgpa;
    }

    public void setCgpa(double cgpa) {
        this.cgpa = cgpa;
    }
}
