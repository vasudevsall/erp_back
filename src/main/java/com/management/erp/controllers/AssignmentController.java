package com.management.erp.controllers;

import com.management.erp.models.repository.AssignmentModel;
import com.management.erp.models.repository.CourseModel;
import com.management.erp.repositories.AssignmentRepository;
import com.management.erp.repositories.AssignmentSubmitRepository;
import com.management.erp.services.FindCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/course/{id}/assignment")
public class AssignmentController {

    @Autowired
    private FindCourseService findCourseService;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<AssignmentModel> getAllAssignments(
        @PathVariable String id,
        @RequestParam(name = "id") Optional<Long> assignmentId
    ) {
        CourseModel course = findCourseService.findCourse(id);
        if(assignmentId.isPresent()) {
            Optional<AssignmentModel> assignmentOptional = assignmentRepository.findById(assignmentId.get());
            if(assignmentOptional.isEmpty())
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid assignment id");
            List<AssignmentModel> list = new ArrayList<>();
            list.add(assignmentOptional.get());
            return list;
        }
        return assignmentRepository.findAllByCourse(course);
    }

    @RequestMapping(value = "/upcoming", method = RequestMethod.GET)
    public @ResponseBody List<AssignmentModel> getUpcomingAssignments(
        @PathVariable String id
    ) {
        CourseModel course = findCourseService.findCourse(id);
        return assignmentRepository.findAllByCourseAndDueGreaterThanEqual(course, LocalDate.now());
    }
}
