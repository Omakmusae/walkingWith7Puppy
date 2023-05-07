package com.turkey.walkingwith7puppy.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.turkey.walkingwith7puppy.annotation.Email;

public class EmailValidator implements ConstraintValidator<Email, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		Pattern pattern = Pattern.compile("\\w+@\\w+\\.\\w+(\\.\\w+)?");
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}
}
