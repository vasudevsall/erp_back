package com.management.erp.controllers.student;

import com.management.erp.models.repository.StudentModel;
import com.management.erp.repositories.StudentRepository;
import com.management.erp.services.FindStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@Controller
@RequestMapping(value = "/student")
public class StudentDetailsController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FindStudentService findStudentService;

    @RequestMapping(value = "/details", method = RequestMethod.GET)
    private @ResponseBody StudentModel getDetails(
            Principal principal, HttpServletResponse httpServletResponse
    ) {
        System.out.println(principal.getName());
        return findStudentService.findStudentModelByEmail(principal.getName());
    }
}
