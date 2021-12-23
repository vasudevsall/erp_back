package com.management.erp.services;

import com.management.erp.models.repository.StudentModel;
import com.management.erp.models.repository.UserModel;
import com.management.erp.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
public class FindStudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FindUserService findUserService;

    public StudentModel findStudentModelById(String id) {
        Optional<StudentModel> studentModelOptional =
                studentRepository.findById(id);
        if(studentModelOptional.isPresent())
            return studentModelOptional.get();

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Student " + id + "not found"
        );
    }

    public StudentModel findStudentModelByUserModel(UserModel userModel) {
        Optional<StudentModel> studentModelOptional =
                studentRepository.findByUserId(userModel);
        if(studentModelOptional.isPresent())
            return studentModelOptional.get();

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Student " + userModel.getEmail() + " not found"
        );
    }

    public StudentModel findStudentModelByEmail(String email) {
        UserModel userModel = findUserService.findUserByEmail(email);
        return findStudentModelByUserModel(userModel);
    }
}
