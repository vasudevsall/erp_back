package com.management.erp.controllers.faculty;

import com.management.erp.models.repository.AttendanceModel;
import com.management.erp.models.repository.CourseModel;
import com.management.erp.models.repository.StudentModel;
import com.management.erp.models.repository.TimeTableModel;
import com.management.erp.models.response.AttendanceResponse;
import com.management.erp.repositories.AttendanceRepository;
import com.management.erp.repositories.TimeTableRepository;
import com.management.erp.services.FindCourseService;
import com.management.erp.services.FindStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping(value = "/faculty/course")
public class FacultyAttendanceController {

    @Autowired
    private FindStudentService findStudentService;
    @Autowired
    private FindCourseService findCourseService;
    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private TimeTableRepository timeTableRepository;

    // Post Attendance
    @RequestMapping(value = "/{id}/attendance", method = RequestMethod.POST)
    public @ResponseBody Map<String, Boolean> updateAttendance(
            @PathVariable String id, @RequestBody AttendanceResponse attendanceResponse
    ) {
        CourseModel courseModel = findCourseService.findCourse(id);

        Map<String, Boolean> studentMap = attendanceResponse.getStudents();
        Set<String> studentList = studentMap.keySet();

        LocalDate date = attendanceResponse.getDate();
        TimeTableModel schedule = getSchedule(courseModel, attendanceResponse);

        for(String stud: studentList) {
            StudentModel student = findStudentService.findStudentModelById(stud);
            Optional<AttendanceModel> attendanceModel =
                attendanceRepository.findAttendanceModelByStudentModelAndDateAndTimetable(
                    student, date, schedule
                );
            if(attendanceModel.isEmpty()) {
                attendanceRepository.save(new AttendanceModel(student, date, schedule, studentMap.get(stud)));
            } else {
                AttendanceModel past = attendanceModel.get();
                past.setPresent(studentMap.get(stud));
                attendanceRepository.save(past);
            }
        }

        return studentMap;
    }

    // Get all attendance for a day of a course
    @RequestMapping(value = "/{id}/attendance", method = RequestMethod.GET)
    public @ResponseBody AttendanceResponse getAllAttendance(
        @PathVariable String id, @RequestBody AttendanceResponse attendanceResponse
    ) {
        CourseModel courseModel = findCourseService.findCourse(id);

        LocalDate date = attendanceResponse.getDate();
        TimeTableModel schedule = getSchedule(courseModel, attendanceResponse);

        List<AttendanceModel> attendances = attendanceRepository.findAllByDateAndTimetable(date, schedule);

        Map<String, Boolean> studentAttendance = new HashMap<>();
        for(AttendanceModel attendance: attendances) {
            studentAttendance.put(attendance.getStudentModel().getId(), attendance.isPresent());
        }

        attendanceResponse.setStudents(studentAttendance);

        return attendanceResponse;
    }

    // Get a student's attendance in a course
    @RequestMapping(value = "/{id}/attendance/student/{stud}", method = RequestMethod.GET)
    public @ResponseBody List<AttendanceModel> getStudentAttendance(
        @PathVariable String id, @PathVariable String stud
    ) {
        CourseModel courseModel = findCourseService.findCourse(id);
        StudentModel studentModel = findStudentService.findStudentModelById(stud);

        List<TimeTableModel> schedules = timeTableRepository.findAllByCourseModel(courseModel);

        List<AttendanceModel> attendances = new ArrayList<>();

        for(TimeTableModel schedule: schedules) {
            attendances.addAll(
                attendanceRepository.findAllByStudentModelAndTimetable(studentModel, schedule)
            );
        }

        return attendances;
    }

    // Helper method to get schedule
    private TimeTableModel getSchedule(CourseModel courseModel, AttendanceResponse attendanceResponse) {
        LocalDate date = attendanceResponse.getDate();
        DayOfWeek day = date.getDayOfWeek();
        int hours = attendanceResponse.getHours();

        Optional<TimeTableModel> timeTableModel =
                timeTableRepository.findByCourseModelAndDayAndHour(courseModel, day, hours);

        if(timeTableModel.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Scheduled class not found");

        return timeTableModel.get();
    }
}
