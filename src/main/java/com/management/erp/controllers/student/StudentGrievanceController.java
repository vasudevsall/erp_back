package com.management.erp.controllers.student;

import com.management.erp.models.custom.GrievanceReplyResponseModel;
import com.management.erp.models.custom.GrievanceResponseModel;
import com.management.erp.models.repository.*;
import com.management.erp.repositories.GrievanceFilesRepository;
import com.management.erp.repositories.GrievanceReplyRepository;
import com.management.erp.repositories.GrievanceRepository;
import com.management.erp.services.FindStudentService;
import com.management.erp.services.FindUserService;
import com.management.erp.utils.GrievanceStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/student/grievance")
public class StudentGrievanceController {

    @Autowired
    FindStudentService findStudentService;
    @Autowired
    FindUserService findUserService;

    @Autowired
    GrievanceRepository grievanceRepository;
    @Autowired
    GrievanceReplyRepository grievanceReplyRepository;
    @Autowired
    GrievanceFilesRepository grievanceFilesRepository;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    List<GrievanceResponseModel> getAllGrievances(Principal principal) {
        StudentModel student = findStudentService.findStudentModelByEmail(principal.getName());

        List<GrievanceModel> grievances = grievanceRepository.findAllByStudent(student);

        grievances.sort(new Comparator<GrievanceModel>() {
            @Override
            public int compare(GrievanceModel o1, GrievanceModel o2) {
                return o1.getDatetime().compareTo(o2.getDatetime()) * -1;
            }
        });

        List<GrievanceResponseModel> responses = new ArrayList<>();
        for(GrievanceModel grievance: grievances)
            responses.add(generateGrievance(grievance));

        return responses;
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody GrievanceResponseModel postNewGrievance(GrievanceResponseModel grievance, Principal principal) {
        StudentModel student = findStudentService.findStudentModelByEmail(principal.getName());

        GrievanceModel grievanceModel = grievance.getGrievance();
        grievanceModel.setStudent(student);
        grievanceModel.setDatetime(LocalDateTime.now());
        grievanceModel.setForwardedTo(null);
        grievanceModel.setStatus(GrievanceStatus.NOT_READ);
        GrievanceModel model = grievanceRepository.save(grievanceModel);

        List<GrievanceFilesModel> files = grievance.getFiles();
        List<GrievanceFilesModel> returnFiles = new ArrayList<>();

        for(GrievanceFilesModel file: files) {
            file.setGrievance(model);
            file.setReply(null);
            returnFiles.add(grievanceFilesRepository.save(file));
        }

        grievance.setGrievance(grievanceModel);
        grievance.setFiles(returnFiles);
        return grievance;
    }

    @RequestMapping(value = "reply", method = RequestMethod.POST)
    public @ResponseBody GrievanceReplyResponseModel postGrievanceReply(
            Principal principal, GrievanceReplyResponseModel replyResponseModel
    ) {
        UserModel user = findUserService.findUserByEmail(principal.getName());

        GrievanceReplyModel replyModel = replyResponseModel.getReply();

        Optional<GrievanceModel> grievanceModelOptional =
                grievanceRepository.findById(replyModel.getGrievance().getId());

        if(grievanceModelOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Grievance not found");

        GrievanceModel grievanceModel = grievanceModelOptional.get();

        replyModel.setGrievance(grievanceModel);
        replyModel.setDatetime(LocalDateTime.now());
        replyModel.setSender(user);
        GrievanceReplyModel grievanceReplyModel = grievanceReplyRepository.save(replyModel);

        List<GrievanceFilesModel> files = replyResponseModel.getFiles();
        List<GrievanceFilesModel> returnFiles = new ArrayList<>();

        for(GrievanceFilesModel file: files) {
            file.setGrievance(null);
            file.setReply(grievanceReplyModel);
            returnFiles.add(grievanceFilesRepository.save(file));
        }

        replyResponseModel.setReply(grievanceReplyModel);
        replyResponseModel.setFiles(returnFiles);
        return replyResponseModel;
    }

    // Helpers

    public GrievanceResponseModel generateGrievance(GrievanceModel grievance) {
        List<GrievanceReplyResponseModel> replies = new ArrayList<>();
        List<GrievanceReplyModel> replyModels = grievanceReplyRepository.findAllByGrievance(grievance);
        replyModels.sort(new Comparator<GrievanceReplyModel>() {
            @Override
            public int compare(GrievanceReplyModel o1, GrievanceReplyModel o2) {
                return o1.getDatetime().compareTo(o2.getDatetime());
            }
        });

        for (GrievanceReplyModel reply : replyModels) {
            replies.add(new GrievanceReplyResponseModel(reply, grievanceFilesRepository.findAllByReply(reply)));
        }


        return new GrievanceResponseModel(grievance, replies, grievanceFilesRepository.findAllByGrievance(grievance));
    }
}
