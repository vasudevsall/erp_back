package com.management.erp.controllers;

import com.management.erp.models.repository.*;
import com.management.erp.repositories.CourseRepository;
import com.management.erp.repositories.TimeTableRepository;
import com.management.erp.services.FindCourseService;
import com.management.erp.services.FindFacultyService;
import com.management.erp.services.FindStudentService;
import com.management.erp.services.FindUserService;
import com.management.erp.utils.TimeTableComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/timetable")
public class TimeTableController {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private TimeTableRepository timeTableRepository;

    @Autowired
    private FindFacultyService findFacultyService;
    @Autowired
    private FindUserService findUserService;
    @Autowired
    private FindStudentService findStudentService;
    @Autowired
    private FindCourseService findCourseService;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<TimeTableModel> getTimeTable(Principal principal) {
        List<CourseModel> courses = getCourses(principal.getName());

        List<TimeTableModel> timeTables = new ArrayList<>();
        for(CourseModel course: courses)
            timeTables.addAll(timeTableRepository.findAllByCourseModel(course));

        timeTables.sort(new TimeTableComparator());

        return timeTables;
    }

    @RequestMapping(value = "/today", method = RequestMethod.GET)
    public @ResponseBody List<TimeTableModel> getTodaySchedule(Principal principal) {
        List<CourseModel> courses = getCourses(principal.getName());

        LocalDateTime localDateTime = LocalDateTime.now();
        DayOfWeek day = localDateTime.getDayOfWeek();

        List<TimeTableModel> upcoming = new ArrayList<>();
        for(CourseModel course: courses) {
            upcoming.addAll(timeTableRepository.findAllByCourseModelAndDay(course, day));
        }
        return upcoming;
    }

    @RequestMapping(value = "/next", method = RequestMethod.GET)
    public @ResponseBody List<TimeTableModel> getNextSchedule(Principal principal) {
        List<CourseModel> courses = getCourses(principal.getName());

        LocalDateTime localDateTime = LocalDateTime.now();
        DayOfWeek day = localDateTime.getDayOfWeek();
        int hour = localDateTime.getHour();

        List<TimeTableModel> upcoming = new ArrayList<>();
        for(CourseModel course: courses)
            upcoming.addAll(timeTableRepository.findAllByCourseModelAndDayAndHourGreaterThanEqual(
                course, day, hour
            ));

        upcoming.sort(new TimeTableComparator());

        return upcoming;
    }

    @RequestMapping(value = "/now", method = RequestMethod.GET)
    public @ResponseBody TimeTableModel getOngoing(Principal principal) {
        List<CourseModel> courses = getCourses(principal.getName());

        LocalDateTime localDateTime = LocalDateTime.now();
        DayOfWeek day = localDateTime.getDayOfWeek();
        int hour = localDateTime.getHour();
        int min = localDateTime.getMinute();

        for(CourseModel course: courses) {
            List<TimeTableModel> schedules = timeTableRepository.findAllByCourseModelAndDayAndHourGreaterThanEqual(
                course, day, (hour - 3)
            );
            for(TimeTableModel schedule: schedules) {
                int h1 = schedule.getHour();
                int m1 = schedule.getMinute();
                int d = schedule.getDuration();
                boolean flag = false;
                if(hour >= h1 && hour <= (h1 +d) ) {
                    if(hour == h1 && min >= m1) flag = true;
                    if(hour == (h1 + d) && min <= m1)   flag = true;
                    if(hour != h1)  flag = true;
                }

                if(flag)
                    return schedule;
            }
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No ongoing class");
    }

    // Helper
    private List<CourseModel> getCourses(String email) {
        UserModel user = findUserService.findUserByEmail(email);

        FacultyModel faculty;
        StudentModel student;
        List<CourseModel> courses = new ArrayList<>();

        if(user.getRoles().equals("ROLE_FACULTY")) {
            faculty = findFacultyService.findFacultyModelByUserModel(user);
            courses = courseRepository.findAllByFaculty(faculty);
        } else {
            student = findStudentService.findStudentModelByUserModel(user);
            List<CourseEnrolModel> courseEnrols = findCourseService.getAllStudentCourses(student);
            for(CourseEnrolModel courseEnrol: courseEnrols)
                courses.add(courseEnrol.getCourseId());
        }

        return courses;
    }
}
