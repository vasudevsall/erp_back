package com.management.erp.controllers.student;

import com.management.erp.models.repository.*;
import com.management.erp.models.custom.StudentAttendanceListModel;
import com.management.erp.repositories.AttendanceRepository;
import com.management.erp.repositories.TimeTableRepository;
import com.management.erp.services.FindCourseService;
import com.management.erp.services.FindStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/student/course")
public class StudentAttendanceController {

    @Autowired
    private FindStudentService findStudentService;
    @Autowired
    private FindCourseService findCourseService;
    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private TimeTableRepository timeTableRepository;

    // Get percentage of attendance in each course
    @RequestMapping(value = "/attendance", method = RequestMethod.GET)
    public @ResponseBody List<StudentAttendanceListModel> getAttendance(
        Principal principal
    ) {
        StudentModel studentModel = findStudentService.findStudentModelByEmail(principal.getName());
        List<CourseEnrolModel> courseEnrols = findCourseService.getAllStudentCourses(studentModel);

        List<StudentAttendanceListModel> attendanceList = new ArrayList<>();

        String[] courseTypes = new String[]{"Lecture", "Tutorial", "Practical"};

        for(CourseEnrolModel courseEnrol: courseEnrols) {
            CourseModel course = courseEnrol.getCourseId();

            for (String courseType : courseTypes) {
                if(course.getTheory() == 0.0 && courseType.equals("Lecture")) continue;
                if(course.getPractical() == 0.0 && courseType.equals("Practical")) continue;
                if(course.getTutorial() == 0.0 && courseType.equals("Tutorial")) continue;

                List<TimeTableModel> schedules =
                        timeTableRepository.findAllByCourseModelAndType(course, courseType.charAt(0));

                int present = 0;
                int absent = 0;

                for (TimeTableModel schedule : schedules) {
                    present += attendanceRepository.findAllByStudentModelAndTimetableAndPresent(
                            studentModel, schedule, true
                    ).size();

                    absent += attendanceRepository.findAllByStudentModelAndTimetableAndPresent(
                            studentModel, schedule, false
                    ).size();
                }

                attendanceList.add(new StudentAttendanceListModel(
                    course.getId(), (course.getName() + " " + courseType),
                    (present + absent), present, absent, new ArrayList<>()
                ));
            }
        }
        return attendanceList;
    }

    // Get attendance details of a single course
    @RequestMapping(value = "/{id}/attendance", method = RequestMethod.GET)
    public @ResponseBody StudentAttendanceListModel getAttendanceForCourse(
            @PathVariable String id, @RequestParam Optional<Character> type,
            Principal principal
    ) {
        StudentModel student = findStudentService.findStudentModelByEmail(principal.getName());
        CourseModel course = findCourseService.findCourse(id);

        char lectureType;
        if(type.isEmpty())  lectureType = 'L';
        else lectureType = type.get();

        List<TimeTableModel> schedules = timeTableRepository.findAllByCourseModelAndType(
            course, lectureType
        );

        List<AttendanceModel> attendances = new ArrayList<>();
        int present = 0;
        int absent = 0;

        for(TimeTableModel schedule: schedules) {
            List<AttendanceModel> presentList = attendanceRepository.findAllByStudentModelAndTimetableAndPresent(
                student, schedule, true
            );
            present += presentList.size();
            attendances.addAll(presentList);

            List<AttendanceModel> absentList = attendanceRepository.findAllByStudentModelAndTimetableAndPresent(
                student, schedule, false
            );
            absent += absentList.size();
            attendances.addAll(absentList);
        }

        attendances.sort(new Comparator<AttendanceModel>() {
            @Override
            public int compare(AttendanceModel o1, AttendanceModel o2) {
                LocalDate d1 = o1.getDate();
                LocalDate d2 = o2.getDate();

                if(d1.equals(d2))   return 0;
                if(d1.isBefore(d2)) return -1;
                return 1;
            }
        });

        return new StudentAttendanceListModel(
            course.getId(), course.getName(), (present + absent), present, absent, attendances
        );
    }
}
