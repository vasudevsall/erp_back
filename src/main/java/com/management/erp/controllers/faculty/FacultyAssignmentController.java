package com.management.erp.controllers.faculty;

import com.management.erp.models.repository.AssignmentModel;
import com.management.erp.models.repository.AssignmentSubmitModel;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/faculty/course/{id}/assignment")
public class FacultyAssignmentController {

    @Autowired
    private FindCourseService findCourseService;

    @Autowired
    private AssignmentRepository assignmentRepository;
    @Autowired
    private AssignmentSubmitRepository assignmentSubmitRepository;


    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody AssignmentModel postAssignment(
        @PathVariable String id, @RequestBody AssignmentModel assignmentModel
    ) {
        CourseModel course = findCourseService.findCourse(id);
        assignmentModel.setCourse(course);
        AssignmentModel assignment = assignmentRepository.save(assignmentModel);
        assignment.setTime(LocalDateTime.now());
        return assignment;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public @ResponseBody AssignmentModel updateAssignment(
        @PathVariable String id, @RequestBody AssignmentModel assignmentModel
    ) {
        Optional<AssignmentModel> assignmentOptional =
                assignmentRepository.findById(assignmentModel.getId());
        if(assignmentOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Assignment not found");
        AssignmentModel assignment = assignmentOptional.get();

        String newAssignment = assignmentModel.getAssignment();
        if(newAssignment != null && !newAssignment.trim().equals(""))
            assignment.setAssignment(newAssignment);

        String newDescription = assignmentModel.getDescription();
        if(newDescription != null && !newDescription.trim().equals(""))
            assignment.setDescription(newDescription);

        int newMarks = assignmentModel.getMax_marks();
        if(newMarks != 0)
            assignment.setMax_marks(newMarks);

        LocalDate newDate = assignmentModel.getDue();
        if(newDate != null)
            assignment.setDue(newDate);

        int newHours = assignmentModel.getDue_hours();
        if(newHours != 0)
            assignment.setDue_hours(newHours);

        int newMinutes = assignmentModel.getDue_minutes();
        if(newMinutes != 0)
            assignment.setDue_minutes(newMinutes);

        return assignmentRepository.save(assignment);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public @ResponseBody AssignmentModel deleteAssignment(
        @PathVariable(name = "id") String course_id,
        @RequestParam(name = "id") long assignmentId
    ) {
        Optional<AssignmentModel> assignmentOptional = assignmentRepository.findById(assignmentId);

        if(assignmentOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Assignment not found");
        AssignmentModel assignment = assignmentOptional.get();

        List<AssignmentSubmitModel> submissions =
                assignmentSubmitRepository.findAllByAssignment(assignment);
        assignmentSubmitRepository.deleteAll(submissions);
        assignmentRepository.delete(assignment);
        return assignment;
    }

    @RequestMapping(value = "/submissions", method = RequestMethod.GET)
    public @ResponseBody List<AssignmentSubmitModel> getSubmissions(
        @PathVariable(name = "id") String courseId,
        @RequestParam(name = "id") long assignmentId
    ) {
        CourseModel course = findCourseService.findCourse(courseId);
        Optional<AssignmentModel> assignmentOptional = assignmentRepository.findById(assignmentId);

        if(assignmentOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Assignment does not exist");

        return assignmentSubmitRepository.findAllByAssignment(assignmentOptional.get());
    }

    @RequestMapping(value = "/submissions", method = RequestMethod.PUT)
    public @ResponseBody AssignmentSubmitModel markSubmission(
            @PathVariable(name = "id") String courseId,
            @RequestBody AssignmentSubmitModel assignmentSubmitModel
    ) {
        CourseModel course = findCourseService.findCourse(courseId);

        Optional<AssignmentSubmitModel> submitOptional =
                assignmentSubmitRepository.findById(assignmentSubmitModel.getId());

        if(submitOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Submission does not exist");

        AssignmentSubmitModel submit = submitOptional.get();

        submit.setMarked(true);
        submit.setMarks(assignmentSubmitModel.getMarks());
        assignmentSubmitRepository.save(submit);
        return submit;
    }
}
