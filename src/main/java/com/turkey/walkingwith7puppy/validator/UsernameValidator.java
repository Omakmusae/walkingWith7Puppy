package com.turkey.walkingwith7puppy.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.turkey.walkingwith7puppy.annotation.Username;

public class UsernameValidator implements ConstraintValidator<Username, String> {

	@Override
	public boolean isValid(final String value, final ConstraintValidatorContext context) {

		Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[0-9]).{4,12}$");
		Matcher matcher = pattern.matcher(value);

		return matcher.matches();
	}
}
