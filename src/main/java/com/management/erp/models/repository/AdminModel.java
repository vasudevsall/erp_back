package com.management.erp.models.repository;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "admin")
public class AdminModel {

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

    private String designation;

    public AdminModel(String id, UserModel userId, DepartmentModel departmentId, String designation) {
        this.id = id;
        this.userId = userId;
        this.departmentId = departmentId;
        this.designation = designation;
    }

    public AdminModel() {}

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

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
