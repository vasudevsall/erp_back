package com.management.erp.models.repository;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "hostel_room")
public class HostelRoomModel {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hostel", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private HostelModel hostel;

    private int roomNo;
    private int floorNo;
    private char block;
    private int capacity;
    private boolean vacant;

    public HostelRoomModel(HostelModel hostel, int roomNo, int floorNo, char block, int capacity, boolean vacant) {
        this.hostel = hostel;
        this.roomNo = roomNo;
        this.floorNo = floorNo;
        this.block = block;
        this.capacity = capacity;
        this.vacant = vacant;
    }

    public HostelRoomModel() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public HostelModel getHostel() {
        return hostel;
    }

    public void setHostel(HostelModel hostel) {
        this.hostel = hostel;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }

    public int getFloorNo() {
        return floorNo;
    }

    public void setFloorNo(int floorNo) {
        this.floorNo = floorNo;
    }

    public char getBlock() {
        return block;
    }

    public void setBlock(char block) {
        this.block = block;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isVacant() {
        return vacant;
    }

    public void setVacant(boolean vacant) {
        this.vacant = vacant;
    }
}
