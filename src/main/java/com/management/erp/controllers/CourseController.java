package com.management.erp.controllers;

import com.management.erp.models.repository.*;
import com.management.erp.models.response.CourseAnnouncementResponseModel;
import com.management.erp.models.response.CourseResponseModel;
import com.management.erp.repositories.*;
import com.management.erp.services.FindCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/course")
public class CourseController {

    @Autowired
    private FindCourseService findCourseService;
    @Autowired
    private CourseAnnouncementRepository courseAnnouncementRepository;
    @Autowired
    private FilesRepository filesRepository;
    @Autowired
    private LinksRepository linksRepository;
    @Autowired
    private CourseEnrolRepository courseEnrolRepository;
    @Autowired
    private EnrolmentRepository enrolmentRepository;
    @Autowired
    private StudentRepository studentRepository;

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

        return new CourseResponseModel(courseModel, announcementResponseModels);
    }

    @RequestMapping(value = "/{id}/students", method = RequestMethod.GET)
    public @ResponseBody List<StudentModel> getCourseStudents(@PathVariable String id) {
        CourseModel courseModel = findCourseService.findCourse(id);
        List<CourseEnrolModel> enrolments = courseEnrolRepository.findAllByCourseId(courseModel);
        List<StudentModel> registeredStudents = new ArrayList<>();
        for(CourseEnrolModel courseEnrolModel: enrolments) {
            if(courseEnrolModel.getSemester() == 0) {
                List<EnrolmentModel> enrolmentModels =
                        enrolmentRepository.findAllByEnrolNo(courseEnrolModel.getEnrolment());
                for(EnrolmentModel enrols: enrolmentModels)
                    registeredStudents.add(enrols.getStudent());
            } else {
                registeredStudents.addAll(studentRepository.findAllBySemesterAndDegree(
                        courseEnrolModel.getSemester(), courseEnrolModel.getDegreeId()
                ));
            }
        }
        return registeredStudents;
    }
}
