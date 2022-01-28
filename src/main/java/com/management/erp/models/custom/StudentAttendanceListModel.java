package com.management.erp.models.custom;

import com.management.erp.models.repository.AttendanceModel;

import java.util.List;

public class StudentAttendanceListModel {

    private String courseId;
    private String courseName;
    private int total;
    private int present;
    private int absent;
    private List<AttendanceModel> attendanceList;

    public StudentAttendanceListModel(
            String courseId, String courseName, int total,
            int present, int absent, List<AttendanceModel> attendanceList
    ) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.total = total;
        this.present = present;
        this.absent = absent;
        this.attendanceList = attendanceList;
    }

    public StudentAttendanceListModel(){}

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPresent() {
        return present;
    }

    public void setPresent(int present) {
        this.present = present;
    }

    public int getAbsent() {
        return absent;
    }

    public void setAbsent(int absent) {
        this.absent = absent;
    }

    public List<AttendanceModel> getAttendanceList() {
        return attendanceList;
    }

    public void setAttendanceList(List<AttendanceModel> attendanceList) {
        this.attendanceList = attendanceList;
    }
}
