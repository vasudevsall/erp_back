package com.management.erp.models.custom;

import com.management.erp.models.repository.GrievanceFilesModel;
import com.management.erp.models.repository.GrievanceModel;

import java.util.List;

public class GrievanceResponseModel {

    private GrievanceModel grievance;
    private List<GrievanceReplyResponseModel> replies;
    private List<GrievanceFilesModel> files;

    public GrievanceResponseModel(GrievanceModel grievance, List<GrievanceReplyResponseModel> replies, List<GrievanceFilesModel> files) {
        this.grievance = grievance;
        this.replies = replies;
        this.files = files;
    }

    public GrievanceResponseModel() {}

    public GrievanceModel getGrievance() {
        return grievance;
    }

    public void setGrievance(GrievanceModel grievance) {
        this.grievance = grievance;
    }

    public List<GrievanceReplyResponseModel> getReplies() {
        return replies;
    }

    public void setReplies(List<GrievanceReplyResponseModel> replies) {
        this.replies = replies;
    }

    public List<GrievanceFilesModel> getFiles() {
        return files;
    }

    public void setFiles(List<GrievanceFilesModel> files) {
        this.files = files;
    }
}
