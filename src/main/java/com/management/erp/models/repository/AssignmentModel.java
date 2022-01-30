package com.management.erp.models.repository;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "assignment")
public class AssignmentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private CourseModel course;

    private String assignment;
    private String description;

    private LocalDateTime time;
    private LocalDate due;
    private int due_hours;
    private int due_minutes;
    private int max_marks;

    public AssignmentModel(
        CourseModel course, String assignment, String description,
       LocalDateTime time, LocalDate due, int due_hours,
       int due_minutes, int max_marks)
    {
        this.course = course;
        this.assignment = assignment;
        this.description = description;
        this.time = time;
        this.due = due;
        this.due_hours = due_hours;
        this.due_minutes = due_minutes;
        this.max_marks = max_marks;
    }

    public AssignmentModel() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CourseModel getCourse() {
        return course;
    }

    public void setCourse(CourseModel course) {
        this.course = course;
    }

    public String getAssignment() {
        return assignment;
    }

    public void setAssignment(String assignment) {
        this.assignment = assignment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public LocalDate getDue() {
        return due;
    }

    public void setDue(LocalDate due) {
        this.due = due;
    }

    public int getDue_hours() {
        return due_hours;
    }

    public void setDue_hours(int due_hours) {
        this.due_hours = due_hours;
    }

    public int getDue_minutes() {
        return due_minutes;
    }

    public void setDue_minutes(int due_minutes) {
        this.due_minutes = due_minutes;
    }

    public int getMax_marks() {
        return max_marks;
    }

    public void setMax_marks(int max_marks) {
        this.max_marks = max_marks;
    }
}
