package com.management.erp.controllers;

import com.management.erp.models.custom.PasswordChangeModel;
import com.management.erp.models.repository.OtpModel;
import com.management.erp.models.repository.UserModel;
import com.management.erp.repositories.OtpRepository;
import com.management.erp.repositories.UserRepository;
import com.management.erp.services.EmailSenderService;
import com.management.erp.services.FindUserService;
import com.management.erp.services.PassGenerator;
import com.management.erp.utils.UserValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    FindUserService findUserService;
    @Autowired
    EmailSenderService emailService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;
    @Autowired
    OtpRepository otpRepository;

    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/login-verify", method = RequestMethod.GET)
    public @ResponseBody UserModel verifyLogin(Principal principal, HttpServletResponse httpServletResponse) {
        try {
            Optional<UserModel> userModelOptional = userRepository.findByEmail(principal.getName());
            if(userModelOptional.isPresent())   return userModelOptional.get();
        } catch (Exception e){
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not logged in");
        }
        return new UserModel();
    }

    @RequestMapping(value = "user/forgot", method = RequestMethod.GET)
    public @ResponseBody String forgotPasswordOtpGeneration(@RequestParam(name = "email") String email) {
        UserModel userModel = findUserService.findUserByEmail(email);
        int otp = PassGenerator.generateOtp();
        Optional<OtpModel> otpModelOptional = otpRepository.findByUser(userModel);
        OtpModel otpModel;
        if(otpModelOptional.isEmpty())  otpModel = new OtpModel(userModel, otp, LocalDateTime.now());
        else {
            otpModel = otpModelOptional.get();
            otpModel.setPassword(otp);
            otpModel.setTime(LocalDateTime.now());
        }

        try {
            otpRepository.save(otpModel);
            emailService.sendOtpMail(userModel.getEmail(), userModel.getFirstName(), otp);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Cannot generate OTP at the moment, please try again later");
        }
        return "OTP has been mailed to you, please check your mail.";
    }

    @RequestMapping(value = "/user/verify/otp", method = RequestMethod.POST)
    public @ResponseBody String verifyOtp(@RequestParam(name = "email") String email,
                                          @RequestBody long otp) {
        UserModel userModel = findUserService.findUserByEmail(email);
        Optional<OtpModel> otpModelOptional =
                otpRepository.findByUserAndTimeAfter(userModel, LocalDateTime.now().minusMinutes(5));
        if(otpModelOptional.isPresent()) {
            OtpModel otpModel = otpModelOptional.get();

            if(otp == otpModel.getPassword()) {
                long newOtp = PassGenerator.generateOtp();
                otpModel.setPassword(newOtp);
                otpModel.setTime(LocalDateTime.now());

                otpRepository.save(otpModel);
                return Long.toString(newOtp);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "OTP did not match");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please generate new OTP");
        }
    }

    @RequestMapping(value = "user/forgot", method = RequestMethod.POST)
    public @ResponseBody String forgotPassword(@RequestParam(name = "email") String email,
                                               @RequestBody PasswordChangeModel passwordChangeModel)
    {
        try {
            UserModel userModel = findUserService.findUserByEmail(email); // Search user
            long otp = Long.parseLong(passwordChangeModel.getPassword()); // Get OTP
            Optional<OtpModel> otpModelOptional =
                    otpRepository.findByUserAndTimeAfter(userModel, LocalDateTime.now().minusMinutes(5)); // Search OTP in database
            if(otpModelOptional.isPresent()) {
                OtpModel otpModel = otpModelOptional.get();

                if(otp == otpModel.getPassword()) { // If otp matches

                    // validate password
                    UserValidationUtil userValidationUtil = new UserValidationUtil();
                    userValidationUtil.passwordValidation(passwordChangeModel.getNewPassword());

                    // change password
                    userModel.setPassword(passwordEncoder.encode(passwordChangeModel.getNewPassword()));
                    userRepository.save(userModel);

                    // make time 1 hour earlier to invalidate otp
                    otpModel.setTime(LocalDateTime.now().minusHours(1));
                    otpRepository.save(otpModel);

                    emailService.sendPasswordChangeMail(userModel.getEmail(), userModel.getFirstName());

                } else { // If otp does not match
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please check OTP entered");
                }
            } else { // No otp in Database
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "OTP expired for user: "+ email);
            }

        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid OTP");
        } catch (ResponseStatusException e) {
            throw  e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to verify now");
        }
        return "Password changed successfully!";
    }
}
