package com.management.erp.services;

import com.management.erp.models.repository.FacultyModel;
import com.management.erp.models.repository.UserModel;
import com.management.erp.repositories.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
public class FindFacultyService {

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private FindUserService findUserService;

    public FacultyModel findFacultyModelById(String id) {
        Optional<FacultyModel> facultyModelOptional =
                facultyRepository.findById(id);
        if(facultyModelOptional.isPresent())
            return facultyModelOptional.get();

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Faculty " + id + "not found"
        );
    }

    public FacultyModel findFacultyModelByUserModel(UserModel userModel) {
        Optional<FacultyModel> facultyModelOptional =
                facultyRepository.findByUserId(userModel);
        if(facultyModelOptional.isPresent())
            return facultyModelOptional.get();

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Faculty " + userModel.getEmail() + " not found"
        );
    }

    public FacultyModel findFacultyModelByEmail(String email) {
        UserModel userModel = findUserService.findUserByEmail(email);
        return findFacultyModelByUserModel(userModel);
    }
}
