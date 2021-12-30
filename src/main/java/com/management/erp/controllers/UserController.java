package com.management.erp.controllers;

import com.management.erp.models.repository.UserModel;
import com.management.erp.repositories.UserRepository;
import com.management.erp.services.FindUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FindUserService findUserService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public @ResponseBody UserModel getUserDetails(Principal principal) {
        return findUserService.findUserByEmail(principal.getName());
    }

    @RequestMapping(value = "/details", method = RequestMethod.PUT)
    public @ResponseBody UserModel updateUserDetails(
            @RequestBody UserModel userModel, Principal principal
    ) {
        UserModel user = findUserService.findUserByEmail(principal.getName());

        String password = userModel.getPassword();
        if(password != null && !password.equals("")) {
            passwordValidation(password);
            user.setPassword(passwordEncoder.encode(password));
        }

        String phone = userModel.getPhone();
        if(phone != null && !phone.equals("")) {
            phoneNumberValidation(phone);
            user.setPhone(phone);
        }

        String profile = userModel.getProfile();
        if(profile != null)
            user.setProfile(profile);

        try {
            userRepository.save(user);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Some error occurred");
        }

        return user;
    }

    // Helper Methods
    public void passwordValidation(String password) {
        if(password.length() < 6) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must be at least 6 characters long");
        }
        if(!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[!@#$%^&+=])(?=\\S+$).{6,}$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must contain at lest one number and a special character and no spaces");
        }
    }

    public void phoneNumberValidation(String phone) {
        if(phone.length() != 10) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone Number must have 10 digits");
        }
        try {
            long phoneNum = Long.parseLong(phone);
        } catch (NumberFormatException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone number can contain only digits");
        }
    }
}
