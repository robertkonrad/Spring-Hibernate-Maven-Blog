package com.robertkonrad.blog.validation;

import com.robertkonrad.blog.entity.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserMatchesPasswordValidator implements ConstraintValidator<UserMatchesPassword, Object> {
    @Override
    public void initialize(UserMatchesPassword constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        User user = (User) obj;
        return user.getPassword().matches(user.getMatchingPassword());
    }

}
