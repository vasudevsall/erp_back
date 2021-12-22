package com.management.erp.models.repository;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "degree")
public class DegreeModel {

    @Id
    private long id;

    private String name;
    private int duration;

    public DegreeModel(String name, int duration) {
        this.name = name;
        this.duration = duration;
    }

    public DegreeModel() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
