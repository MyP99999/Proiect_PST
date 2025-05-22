package com.FCSB.FCSB.validations;

import com.FCSB.FCSB.entities.Employee;
import com.FCSB.FCSB.services.EmployeeService;
import com.FCSB.FCSB.utils.LogAndResponseUtil;
import com.FCSB.FCSB.auth.RegisterRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RegisterValidation {

    @Autowired
    private EmployeeService userService;

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private LogAndResponseUtil logAndResponseUtil;

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@#$%^&+=!])([A-Za-z\\d@#$%^&+=!]{10,})$";

    public ResponseEntity<Object> validateRegistration(RegisterRequest request) {
        String methodName = "validateRegistration - ";

        // Check if the user already exists
        Optional<Employee> existingUser = userService.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            return logAndResponseUtil.generate(logger, methodName, "User already exists with this username", HttpStatus.CONFLICT, null);
        }

        // Check if the email already exists
        Optional<Employee> existingEmailUser = userService.findByEmail(request.getEmail());
        if (existingEmailUser.isPresent()) {
            return logAndResponseUtil.generate(logger, methodName, "Email already in use", HttpStatus.CONFLICT, null);
        }

        if (!request.getEmail().matches(EMAIL_REGEX)) {
            return logAndResponseUtil.generate(logger, methodName, "Invalid email format", HttpStatus.BAD_REQUEST, null);
        }

        if (!request.getPassword().matches(PASSWORD_REGEX)) {
            return logAndResponseUtil.generate(logger, methodName, "Invalid password format", HttpStatus.BAD_REQUEST, null);
        }

        return null;
    }
}