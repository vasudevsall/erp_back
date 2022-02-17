package com.management.erp.models.repository;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "hostel")
public class HostelModel {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private String name;
    private int single;
    private int doublet;
    private int triple;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "warden", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private FacultyModel warden;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "joint", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private FacultyModel joint;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "caretaker", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private UserModel caretaker;

    public HostelModel(String name, int single, int doublet, int triple, FacultyModel warden, FacultyModel joint, UserModel caretaker) {
        this.name = name;
        this.single = single;
        this.doublet = doublet;
        this.triple = triple;
        this.warden = warden;
        this.joint = joint;
        this.caretaker = caretaker;
    }

    public HostelModel() {}

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

    public int getSingle() {
        return single;
    }

    public void setSingle(int single) {
        this.single = single;
    }

    public int getDoublet() {
        return doublet;
    }

    public void setDoublet(int doublet) {
        this.doublet = doublet;
    }

    public int getTriple() {
        return triple;
    }

    public void setTriple(int triple) {
        this.triple = triple;
    }

    public FacultyModel getWarden() {
        return warden;
    }

    public void setWarden(FacultyModel warden) {
        this.warden = warden;
    }

    public FacultyModel getJoint() {
        return joint;
    }

    public void setJoint(FacultyModel joint) {
        this.joint = joint;
    }

    public UserModel getCaretaker() {
        return caretaker;
    }

    public void setCaretaker(UserModel caretaker) {
        this.caretaker = caretaker;
    }
}
