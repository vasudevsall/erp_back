package com.management.erp.models.repository;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name ="hostel_reg")
public class HostelRegModel {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private HostelRoomModel room;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private StudentModel student;

    public HostelRegModel(HostelRoomModel room, StudentModel student) {
        this.room = room;
        this.student = student;
    }

    public HostelRegModel() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public HostelRoomModel getRoom() {
        return room;
    }

    public void setRoom(HostelRoomModel room) {
        this.room = room;
    }

    public StudentModel getStudent() {
        return student;
    }

    public void setStudent(StudentModel student) {
        this.student = student;
    }
}
