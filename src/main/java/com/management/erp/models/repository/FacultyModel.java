package com.management.erp.models.repository;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "faculty")
public class FacultyModel {

    @Id
    private String id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private UserModel userId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private DepartmentModel departmentId;

    private String qualification;

    public FacultyModel(String id, UserModel userId, DepartmentModel departmentId, String qualification) {
        this.id = id;
        this.userId = userId;
        this.departmentId = departmentId;
        this.qualification = qualification;
    }

    public FacultyModel() {}

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

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }
}
