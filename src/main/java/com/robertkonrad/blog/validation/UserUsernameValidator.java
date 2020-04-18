package com.robertkonrad.blog.validation;

import com.robertkonrad.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserUsernameValidator implements ConstraintValidator<UserUsername, String> {

    @Autowired
    private UserService userService;

    @Override
    public void initialize(UserUsername constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return userService.usernameAvailable(value);
    }
}
