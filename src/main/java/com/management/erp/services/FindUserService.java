package com.management.erp.services;

import com.management.erp.models.repository.UserModel;
import com.management.erp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
public class FindUserService {

    @Autowired
    private UserRepository userRepository;

    public UserModel findUserByEmail(String email) {
        Optional<UserModel> userModelOptional =
                userRepository.findByEmail(email);

        if(userModelOptional.isPresent())
            return userModelOptional.get();

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User " + email + " not found"
        );
    }
}
