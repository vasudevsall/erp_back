package com.management.erp.models.repository;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "department")
public class DepartmentModel {

    @Id
    private String id;
    private String name;
    private boolean adminDept;

    public DepartmentModel(String id, String name, boolean adminDept) {
        this.id = id;
        this.name = name;
        this.adminDept = adminDept;
    }

    public DepartmentModel() {}

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

    public boolean isAdminDept() {
        return adminDept;
    }

    public void setAdminDept(boolean adminDept) {
        this.adminDept = adminDept;
    }
}
