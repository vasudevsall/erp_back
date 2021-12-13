package com.management.erp.controllers;

import com.management.erp.models.repository.StudentModel;
import com.management.erp.repositories.StudentRepository;
import com.management.erp.services.FindStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@Controller
@RequestMapping(name = "/student")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FindStudentService findStudentService;

    private @ResponseBody StudentModel getDetails(
            Principal principal, HttpServletResponse httpServletResponse
    ) {
        findStudentService.findStudentModelByEmail(principal.getName());
        return new StudentModel();
    }
}
