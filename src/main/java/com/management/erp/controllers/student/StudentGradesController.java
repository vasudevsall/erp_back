package com.management.erp.controllers.student;

import com.management.erp.models.repository.CourseModel;
import com.management.erp.models.repository.GradesModel;
import com.management.erp.models.repository.StudentModel;
import com.management.erp.repositories.GradesRepository;
import com.management.erp.services.FindCourseService;
import com.management.erp.services.FindStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping(value = "/student/grades")
public class StudentGradesController {

    @Autowired
    private FindStudentService findStudentService;
    @Autowired
    private FindCourseService findCourseService;

    @Autowired
    private GradesRepository gradesRepository;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<GradesModel> getAllGrades(Principal principal) {
        StudentModel student = findStudentService.findStudentModelByEmail(principal.getName());
        return gradesRepository.findAllByStudent(student);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody List<GradesModel> getCourseGrades(
        @PathVariable String id, Principal principal
    ) {
        StudentModel student = findStudentService.findStudentModelByEmail(principal.getName());
        CourseModel course = findCourseService.findCourse(id);
        return gradesRepository.findAllByStudentAndCourse(student, course);
    }
}
