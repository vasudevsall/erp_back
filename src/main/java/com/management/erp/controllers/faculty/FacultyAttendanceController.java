package com.management.erp.controllers.faculty;

import com.management.erp.models.repository.AttendanceModel;
import com.management.erp.models.repository.CourseModel;
import com.management.erp.models.repository.StudentModel;
import com.management.erp.models.repository.TimeTableModel;
import com.management.erp.models.response.AttendanceResponse;
import com.management.erp.repositories.AttendanceRepository;
import com.management.erp.repositories.TimeTableRepository;
import com.management.erp.services.CourseStudentService;
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
    private CourseStudentService courseStudentService;

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

    // Get list of all students and their attendances in a course
    @RequestMapping(value = "/{id}/attendance/student", method = RequestMethod.GET)
    public @ResponseBody List<StudentListResponse> getStudentList(
        @PathVariable String id
    ) {
        CourseModel course = findCourseService.findCourse(id);
        List<StudentModel> students = courseStudentService.getAllRegisteredStudents(course);

        List<TimeTableModel> schedules = timeTableRepository.findAllByCourseModel(course);
        List<StudentListResponse> studentAttendances = new ArrayList<>();

        for(StudentModel student: students) {
            int present = 0;
            int absent = 0;

            for(TimeTableModel schedule: schedules) {
                present += attendanceRepository.findAllByStudentModelAndTimetableAndPresent(
                    student, schedule, true
                ).size();
                absent += attendanceRepository.findAllByStudentModelAndTimetableAndPresent(
                    student, schedule, false
                ).size();
            }

            studentAttendances.add(new StudentListResponse(
                student, present, absent
            ));
        }

        return studentAttendances;
    }

    // Helper class
    private class StudentListResponse {
        StudentModel studentModel;
        int present;
        int absent;

        public StudentListResponse(StudentModel studentModel, int present, int absent) {
            this.studentModel = studentModel;
            this.present = present;
            this.absent = absent;
        }

        public StudentListResponse() {}

        public StudentModel getStudentModel() {
            return studentModel;
        }

        public void setStudentModel(StudentModel studentModel) {
            this.studentModel = studentModel;
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
