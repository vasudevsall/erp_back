package com.management.erp.models.repository;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "gate_pass")
public class GatePassModel {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hostel_reg", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private HostelRegModel hostelReg;

    private String purpose;
    private LocalDate date;
    private LocalDate returnDate;

    private boolean permission;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "signed_by", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private FacultyModel signedBy;

    private LocalDateTime signedOn;

    public GatePassModel(HostelRegModel hostelReg, String purpose, LocalDate date, LocalDate returnDate, boolean permission, FacultyModel signedBy, LocalDateTime signedOn) {
        this.hostelReg = hostelReg;
        this.purpose = purpose;
        this.date = date;
        this.returnDate = returnDate;
        this.permission = permission;
        this.signedBy = signedBy;
        this.signedOn = signedOn;
    }

    public GatePassModel() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public HostelRegModel getHostelReg() {
        return hostelReg;
    }

    public void setHostelReg(HostelRegModel hostelReg) {
        this.hostelReg = hostelReg;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isPermission() {
        return permission;
    }

    public void setPermission(boolean permission) {
        this.permission = permission;
    }

    public FacultyModel getSignedBy() {
        return signedBy;
    }

    public void setSignedBy(FacultyModel signedBy) {
        this.signedBy = signedBy;
    }

    public LocalDateTime getSignedOn() {
        return signedOn;
    }

    public void setSignedOn(LocalDateTime signedOn) {
        this.signedOn = signedOn;
    }
}
