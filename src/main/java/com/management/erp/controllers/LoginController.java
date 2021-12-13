package com.management.erp.controllers;

import com.management.erp.models.repository.UserModel;
import com.management.erp.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/login-verify", method = RequestMethod.GET)
    public @ResponseBody UserModel verifyLogin(Principal principal, HttpServletResponse httpServletResponse) {
        Optional<UserModel> userModelOptional = userRepository.findByEmail(principal.getName());
        if(userModelOptional.isPresent())   return userModelOptional.get();
        else {
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not logged in");
        }
    }
}
