package com.management.erp.models.repository;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "degree")
public class DegreeModel {

    @Id
    private int degree;

    private String name;
    private int duration;

    public DegreeModel(String name, int duration) {
        this.name = name;
        this.duration = duration;
    }

    public DegreeModel() {}

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
