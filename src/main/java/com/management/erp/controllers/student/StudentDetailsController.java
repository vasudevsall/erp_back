package com.management.erp.controllers.student;

import com.management.erp.models.repository.CourseEnrolModel;
import com.management.erp.models.repository.StudentModel;
import com.management.erp.repositories.CourseEnrolRepository;
import com.management.erp.repositories.StudentRepository;
import com.management.erp.services.FindCourseService;
import com.management.erp.services.FindStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping(value = "/student")
public class StudentDetailsController {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseEnrolRepository courseEnrolRepository;

    @Autowired
    private FindStudentService findStudentService;
    @Autowired
    private FindCourseService findCourseService;

    @RequestMapping(value = "/details", method = RequestMethod.GET)
    private @ResponseBody StudentModel getDetails(
            Principal principal, HttpServletResponse httpServletResponse
    ) {
        return findStudentService.findStudentModelByEmail(principal.getName());
    }

    @RequestMapping(value = "/courses", method = RequestMethod.GET)
    private @ResponseBody List<CourseEnrolModel> getAllCourses(
            Principal principal, HttpServletResponse httpServletResponse
    ) {
      StudentModel studentModel = findStudentService.findStudentModelByEmail(principal.getName());
      return findCourseService.getAllStudentCourses(studentModel);
    };
}
