package com.management.erp.controllers.faculty;

import com.management.erp.models.repository.CourseModel;
import com.management.erp.models.repository.FacultyModel;
import com.management.erp.models.repository.StudentModel;
import com.management.erp.repositories.CourseRepository;
import com.management.erp.services.FindFacultyService;
import com.management.erp.services.FindStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping(value = "/faculty")
public class FacultyDetailsController {

    @Autowired
    private FindFacultyService findFacultyService;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private FindStudentService findStudentService;

    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public @ResponseBody FacultyModel getFacultyDetails(Principal principal) {
        return findFacultyService.findFacultyModelByEmail(principal.getName());
    }

    @RequestMapping(value = "/courses", method = RequestMethod.GET)
    public @ResponseBody List<CourseModel> findAllCourses(Principal principal) {
        FacultyModel facultyModel = findFacultyService.findFacultyModelByEmail(principal.getName());
        return courseRepository.findAllByFaculty(facultyModel);
    }

    @RequestMapping(value = "/student", method = RequestMethod.GET)
    public @ResponseBody StudentModel findStudentDetails(@RequestParam String entry) {
        return findStudentService.findStudentModelById(entry);
    }
}
