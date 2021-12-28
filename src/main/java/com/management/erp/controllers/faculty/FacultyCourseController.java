package com.management.erp.controllers.faculty;

import com.management.erp.models.repository.CourseAnnouncementModel;
import com.management.erp.models.repository.CourseModel;
import com.management.erp.models.repository.FilesModel;
import com.management.erp.models.repository.LinksModel;
import com.management.erp.models.response.CourseAnnouncementResponseModel;
import com.management.erp.repositories.CourseAnnouncementRepository;
import com.management.erp.repositories.FilesRepository;
import com.management.erp.repositories.LinksRepository;
import com.management.erp.services.FindCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/faculty/course")
public class FacultyCourseController {

    @Autowired
    private FindCourseService findCourseService;
    @Autowired
    private CourseAnnouncementRepository courseAnnouncementRepository;
    @Autowired
    private FilesRepository filesRepository;
    @Autowired
    private LinksRepository linksRepository;

    @RequestMapping(value = "/{id}/announcement", method = RequestMethod.POST)
    public @ResponseBody CourseAnnouncementResponseModel postAnnouncement(
            @PathVariable String id,
            @RequestBody CourseAnnouncementResponseModel courseAnnouncementResponseModel
    ) {
        CourseModel courseModel = findCourseService.findCourse(id);
        CourseAnnouncementModel courseAnnouncementModel =
                courseAnnouncementResponseModel.getAnnouncement();

        courseAnnouncementModel.setCourseId(courseModel);

        CourseAnnouncementModel announcement = courseAnnouncementRepository.save(courseAnnouncementModel);

        List<FilesModel> files = courseAnnouncementResponseModel.getFiles();
        for(FilesModel file: files) {
            file.setAnnouncement(announcement);
            filesRepository.save(file);
        }

        List<LinksModel> links = courseAnnouncementResponseModel.getLinks();
        for(LinksModel link: links) {
            link.setAnnouncement(announcement);
            linksRepository.save(link);
        }

        return new CourseAnnouncementResponseModel(
                announcement,
                filesRepository.findAllByAnnouncement(announcement),
                linksRepository.findAllByAnnouncement(announcement)
        );
    }

    @RequestMapping(value = "/{id}/announcement/{announceId}", method = RequestMethod.DELETE)
    public @ResponseBody CourseAnnouncementResponseModel deleteCourseAnnouncement(
            @PathVariable String id, @PathVariable String announceId
    ) {
        Optional<CourseAnnouncementModel> announcementModelOptional
                = courseAnnouncementRepository.findById(Long.parseLong(announceId));
        if(announcementModelOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Announcement " + announceId + " not found");

        CourseAnnouncementModel announcementModel = announcementModelOptional.get();

        List<FilesModel> files = filesRepository.findAllByAnnouncement(announcementModel);
        List<LinksModel> links = linksRepository.findAllByAnnouncement(announcementModel);

        filesRepository.deleteAll(files);
        linksRepository.deleteAll(links);
        courseAnnouncementRepository.delete(announcementModel);

        return new CourseAnnouncementResponseModel(announcementModel, files, links);
    }
}
