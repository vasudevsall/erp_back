package com.management.erp.services;

import com.management.erp.models.repository.CourseAnnouncementModel;
import com.management.erp.models.repository.CourseModel;
import com.management.erp.repositories.CourseAnnouncementRepository;
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
}
