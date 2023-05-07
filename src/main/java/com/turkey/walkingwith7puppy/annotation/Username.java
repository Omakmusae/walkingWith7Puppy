package com.turkey.walkingwith7puppy.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.turkey.walkingwith7puppy.validator.UsernameValidator;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UsernameValidator.class)
@Documented
public @interface Username {

	String message() default "Invalid Username";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}
