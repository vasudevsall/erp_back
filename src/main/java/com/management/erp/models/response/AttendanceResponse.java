package com.management.erp.models.response;

import java.time.LocalDate;
import java.util.Map;

public class AttendanceResponse {

    private LocalDate date;
    private int hours;
    private int minutes;
    private Map<String, Boolean> students;

    public AttendanceResponse(LocalDate date, int hours, int minutes, Map<String, Boolean> students) {
        this.date = date;
        this.hours = hours;
        this.minutes = minutes;
        this.students = students;
    }

    public AttendanceResponse() {}

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public Map<String, Boolean> getStudents() {
        return students;
    }

    public void setStudents(Map<String, Boolean> students) {
        this.students = students;
    }
}
