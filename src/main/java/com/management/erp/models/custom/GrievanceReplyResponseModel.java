package com.management.erp.models.custom;

import com.management.erp.models.repository.GrievanceFilesModel;
import com.management.erp.models.repository.GrievanceReplyModel;

import java.util.List;

public class GrievanceReplyResponseModel {

    private GrievanceReplyModel reply;
    private List<GrievanceFilesModel> files;

    public GrievanceReplyResponseModel(GrievanceReplyModel reply, List<GrievanceFilesModel> files) {
        this.reply = reply;
        this.files = files;
    }

    public GrievanceReplyResponseModel() {}

    public GrievanceReplyModel getReply() {
        return reply;
    }

    public void setReply(GrievanceReplyModel reply) {
        this.reply = reply;
    }

    public List<GrievanceFilesModel> getFiles() {
        return files;
    }

    public void setFiles(List<GrievanceFilesModel> files) {
        this.files = files;
    }
}
