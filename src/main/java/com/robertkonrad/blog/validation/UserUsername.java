package com.robertkonrad.blog.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UserUsernameValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserUsername {
    public String value() default "";

    public String message() default "User with that username exist already or username cannot be empty!";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}
