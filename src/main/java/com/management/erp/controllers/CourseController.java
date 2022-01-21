package com.management.erp.controllers;

import com.management.erp.models.repository.*;
import com.management.erp.models.response.CourseAnnouncementResponseModel;
import com.management.erp.models.response.CourseResponseModel;
import com.management.erp.repositories.*;
import com.management.erp.services.CourseStudentService;
import com.management.erp.services.FindCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping(value = "/course")
public class CourseController {

    @Autowired
    private FindCourseService findCourseService;
    @Autowired
    private CourseStudentService courseStudentService;

    @Autowired
    private CourseAnnouncementRepository courseAnnouncementRepository;
    @Autowired
    private FilesRepository filesRepository;
    @Autowired
    private LinksRepository linksRepository;
    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private TimeTableRepository timeTableRepository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody CourseResponseModel getCourseAnnouncements(
            @PathVariable String id
    ) {
        CourseModel courseModel = findCourseService.findCourse(id);
        List<CourseAnnouncementModel> courseAnnouncementModels =
                courseAnnouncementRepository.findAllByCourseId(courseModel);

        List<CourseAnnouncementResponseModel> announcementResponseModels =
                new ArrayList<>();

        for(CourseAnnouncementModel announcement: courseAnnouncementModels) {
            List<FilesModel> filesModels = filesRepository.findAllByAnnouncement(announcement);
            List<LinksModel> linksModels = linksRepository.findAllByAnnouncement(announcement);

            announcementResponseModels.add(new CourseAnnouncementResponseModel(
                    announcement, filesModels, linksModels
            ));
        }

        announcementResponseModels.sort(new Comparator<CourseAnnouncementResponseModel>() {
            @Override
            public int compare(CourseAnnouncementResponseModel o1, CourseAnnouncementResponseModel o2) {
                LocalDateTime ld1 = o1.getAnnouncement().getTime();
                LocalDateTime ld2 = o2.getAnnouncement().getTime();

                if(ld1.isBefore(ld2))   return 1;
                if(ld1.isAfter(ld2))    return -1;
                return 0;
            }
        });

        return new CourseResponseModel(courseModel, announcementResponseModels);
    }

    @RequestMapping(value = "/{id}/students", method = RequestMethod.GET)
    public @ResponseBody List<StudentModel> getCourseStudents(@PathVariable String id) {
        CourseModel courseModel = findCourseService.findCourse(id);
        return courseStudentService.getAllRegisteredStudents(courseModel);
    }

    @RequestMapping(value = "/{id}/sessions", method = RequestMethod.GET)
    public @ResponseBody Map<String, List<String>> getSessionList(
        @PathVariable String id
    ) {
        CourseModel course = findCourseService.findCourse(id);
        String[] courseTypes = new String[]{"Lecture", "Tutorial", "Practical"};

        Map<String, List<String>> sessionMap = new HashMap<>();

        for(String type: courseTypes) {
            List<String> sessionList = new ArrayList<>();
            List<TimeTableModel> timeTables = timeTableRepository.findAllByCourseModelAndType(course, type.charAt(0));
            for(TimeTableModel schedule: timeTables) {
                List<Object[]> sessions = attendanceRepository.getSessionListByTimeTable(schedule);
                for(Object[] session: sessions)
                    sessionList.add(session[0].toString());
            }

            sessionMap.put(type, sessionList);
        }

        return sessionMap;
    }
}
