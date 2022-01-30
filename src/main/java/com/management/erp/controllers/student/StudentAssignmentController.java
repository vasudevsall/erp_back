package com.management.erp.controllers.student;

import com.management.erp.models.repository.AssignmentModel;
import com.management.erp.models.repository.AssignmentSubmitModel;
import com.management.erp.models.repository.StudentModel;
import com.management.erp.repositories.AssignmentRepository;
import com.management.erp.repositories.AssignmentSubmitRepository;
import com.management.erp.services.FindStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping(value = "/student/course/{id}/assignment")
public class StudentAssignmentController {

    @Autowired
    private FindStudentService findStudentService;

    @Autowired
    private AssignmentRepository assignmentRepository;
    @Autowired
    private AssignmentSubmitRepository assignmentSubmitRepository;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody AssignmentSubmitModel getSubmission(
        @PathVariable(name = "id") String course_id,
        @RequestParam(name = "id") long assignment_id,
        Principal principal
    ) {
        StudentModel student = findStudentService.findStudentModelByEmail(principal.getName());
        Optional<AssignmentModel> assignmentOptional =
                assignmentRepository.findById(assignment_id);
        if(assignmentOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Assignment not found");

        AssignmentModel assignment = assignmentOptional.get();

        Optional<AssignmentSubmitModel> submitOptional =
                assignmentSubmitRepository.findByAssignmentAndStudent(assignment, student);
        if(submitOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No submission found");
        return submitOptional.get();
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody AssignmentSubmitModel postSubmission(
        @PathVariable(name = "id") String course_id,
        @RequestBody AssignmentSubmitModel assignmentSubmitModel,
        Principal principal
    ) {
        StudentModel student = findStudentService.findStudentModelByEmail(principal.getName());

        Optional<AssignmentModel> assignmentOptional =
                assignmentRepository.findById(assignmentSubmitModel.getAssignment().getId());
        if(assignmentOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Assignment not found");

        AssignmentModel assignment = assignmentOptional.get();

        assignmentSubmitModel.setStudent(student);
        assignmentSubmitModel.setAssignment(assignment);
        assignmentSubmitModel.setMarks(0);

        AssignmentSubmitModel submission = assignmentSubmitRepository.save(assignmentSubmitModel);
        submission.setTime(LocalDateTime.now());

        return submission;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public @ResponseBody AssignmentSubmitModel updateSubmission(
        @PathVariable(name = "id") String course_id,
        @RequestBody AssignmentSubmitModel assignmentSubmitModel,
        Principal principal
    ) {
        AssignmentSubmitModel submit =
            getAssignmentSubmission(assignmentSubmitModel.getId(), principal.getName());

        String newFile = assignmentSubmitModel.getFile();
        if(newFile == null || newFile.equals(""))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid File");

        submit.setFile(newFile);
        return assignmentSubmitRepository.save(submit);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public @ResponseBody AssignmentSubmitModel deleteSubmission(
        @PathVariable(name = "id") String course_id,
        @RequestParam(name = "id") long submission_id,
        Principal principal
    ) {
        AssignmentSubmitModel submit = getAssignmentSubmission(submission_id, principal.getName());
        assignmentSubmitRepository.delete(submit);
        return submit;
    }

    // Helper Method
    private AssignmentSubmitModel getAssignmentSubmission(long id, String user) {
        StudentModel student = findStudentService.findStudentModelByEmail(user);

        Optional<AssignmentSubmitModel> submitOptional = assignmentSubmitRepository.findById(id);

        if(submitOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Submission not found");

        AssignmentSubmitModel submit = submitOptional.get();

        if(!submit.getStudent().getId().equals(student.getId()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not authorized to modify this submission");

        return submit;
    }
}
