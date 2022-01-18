package com.management.erp.services;

import com.management.erp.models.repository.*;
import com.management.erp.repositories.CourseAnnouncementRepository;
import com.management.erp.repositories.CourseEnrolRepository;
import com.management.erp.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Component
public class FindCourseService {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseAnnouncementRepository courseAnnouncementRepository;
    @Autowired
    private CourseEnrolRepository courseEnrolRepository;

    public CourseModel findCourse(String id) {
        Optional<CourseModel> courseModelOptional = courseRepository.findById(id);

        if(courseModelOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course " + id + " not found");
        }

        return courseModelOptional.get();
    }

    public List<CourseAnnouncementModel> getCourseAnnouncements(String id) {
        CourseModel courseModel = findCourse(id);
        return courseAnnouncementRepository.findAllByCourseId(courseModel);
    }

    public List<CourseAnnouncementModel> getCourseAnnouncements(CourseModel courseModel) {
        return courseAnnouncementRepository.findAllByCourseId(courseModel);
    }

    // Find all courses for students
    public List<CourseEnrolModel> getAllStudentCourses(StudentModel studentModel) {
        return courseEnrolRepository.findAllByDegreeIdAndSemester(
                studentModel.getDegree(), studentModel.getSemester()
        );
    }
}
