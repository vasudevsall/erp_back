package com.management.erp.controllers.faculty;

import com.management.erp.models.custom.GrievanceReplyResponseModel;
import com.management.erp.models.custom.GrievanceResponseModel;
import com.management.erp.models.repository.GrievanceFilesModel;
import com.management.erp.models.repository.GrievanceModel;
import com.management.erp.models.repository.GrievanceReplyModel;
import com.management.erp.models.repository.UserModel;
import com.management.erp.repositories.GrievanceFilesRepository;
import com.management.erp.repositories.GrievanceReplyRepository;
import com.management.erp.repositories.GrievanceRepository;
import com.management.erp.services.FindUserService;
import com.management.erp.utils.GrievanceStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/faculty/grievance")
public class FacultyGrievanceController {

    @Autowired
    private FindUserService findUserService;
    @Autowired
    private GrievanceRepository grievanceRepository;
    @Autowired
    private GrievanceReplyRepository grievanceReplyRepository;
    @Autowired
    private GrievanceFilesRepository grievanceFilesRepository;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<GrievanceResponseModel> getGrievances(
            Optional<Integer> status
    ) {
        List<GrievanceResponseModel> responseModels = new ArrayList<>();
        List<GrievanceModel> grievanceModels = new ArrayList<>();
        if(status.isEmpty()) {
            grievanceModels = grievanceRepository.findAllByStatus(GrievanceStatus.NOT_READ);

        } else {
            int st = status.get();
            if(st == 1) {
                grievanceModels = grievanceRepository.findAllByStatus(GrievanceStatus.READ);
            } else if(st == 2) {
                grievanceModels = grievanceRepository.findAllByStatus(GrievanceStatus.WORKING);
            } else {
                grievanceModels = grievanceRepository.findAllByStatus(GrievanceStatus.SOLVED);
            }
        }

        grievanceModels.sort(new Comparator<GrievanceModel>() {
            @Override
            public int compare(GrievanceModel o1, GrievanceModel o2) {
                return o1.getDatetime().compareTo(o2.getDatetime()) * -1;
            }
        });

        List<GrievanceResponseModel> responses = new ArrayList<>();
        for(GrievanceModel grievance: grievanceModels)
            responses.add(generateGrievance(grievance));

        return responses;
    }

    @RequestMapping(value = "reply", method = RequestMethod.POST)
    public @ResponseBody GrievanceReplyResponseModel postGrievanceReply(
            Principal principal, @RequestBody GrievanceReplyResponseModel replyResponseModel
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

    @RequestMapping(method = RequestMethod.PUT)
    public @ResponseBody GrievanceResponseModel updateStatus(
            Principal principal, @RequestParam long id, @RequestParam int status
    ) {
        Optional<GrievanceModel> grievanceModel = grievanceRepository.findById(id);
        if(grievanceModel.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Grievance Not Found");

        GrievanceModel grievance = grievanceModel.get();
        if(status == 1)
            grievance.setStatus(GrievanceStatus.READ);
        else if(status == 2)
            grievance.setStatus(GrievanceStatus.WORKING);
        else
            grievance.setStatus(GrievanceStatus.SOLVED);

        grievanceRepository.save(grievance);

        return generateGrievance(grievance);
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
