package com.management.erp.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserValidationUtil {

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
