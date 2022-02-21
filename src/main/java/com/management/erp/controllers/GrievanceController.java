package com.management.erp.controllers;

import com.management.erp.models.custom.GrievanceReplyResponseModel;
import com.management.erp.models.custom.GrievanceResponseModel;
import com.management.erp.models.repository.GrievanceModel;
import com.management.erp.models.repository.GrievanceReplyModel;
import com.management.erp.repositories.GrievanceFilesRepository;
import com.management.erp.repositories.GrievanceReplyRepository;
import com.management.erp.repositories.GrievanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/grievance")
public class GrievanceController {

    @Autowired
    private GrievanceRepository grievanceRepository;
    @Autowired
    private GrievanceReplyRepository grievanceReplyRepository;
    @Autowired
    private GrievanceFilesRepository grievanceFilesRepository;

    @RequestMapping(method = RequestMethod.GET)
    private @ResponseBody GrievanceResponseModel getGrievance(@RequestParam long id) {
        Optional<GrievanceModel> grievanceModel = grievanceRepository.findById(id);

        if(grievanceModel.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return generateGrievance(grievanceModel.get());
    }

    // Helper
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
