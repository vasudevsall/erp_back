package com.management.erp.controllers;

import com.management.erp.models.repository.UserModel;
import com.management.erp.repositories.UserRepository;
import com.management.erp.services.FindUserService;
import com.management.erp.utils.UserValidationUtil;
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

        UserValidationUtil userValidationUtil = new UserValidationUtil();

        String password = userModel.getPassword();
        if(password != null && !password.equals("")) {
            userValidationUtil.passwordValidation(password);
            user.setPassword(passwordEncoder.encode(password));
        }

        String phone = userModel.getPhone();
        if(phone != null && !phone.equals("")) {
            userValidationUtil.phoneNumberValidation(phone);
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
}
