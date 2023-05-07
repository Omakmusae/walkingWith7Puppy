package com.turkey.walkingwith7puppy.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.turkey.walkingwith7puppy.annotation.Password;

public class PasswordValidator implements ConstraintValidator<Password, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		Pattern pattern = Pattern.compile("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[$&+,:;=?@#|'<>.^*()%!-]).{8,15}$");
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}
}
