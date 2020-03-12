package com.robertkonrad.blog.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PostFileExtensionValidator implements ConstraintValidator<PostFileExtension,String> {
    @Override
    public void initialize(PostFileExtension constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // todo check file extension
        return true;
    }
}
