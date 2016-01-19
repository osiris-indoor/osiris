package com.bitmonlab.core.validations.aspect.contractViolationProcessor.defaultImplementation;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.bitmonlab.core.validations.annotations.ValidateEmailInSet;



public class ValidateEmailInSetImpl implements ConstraintValidator<ValidateEmailInSet, Map<String, Object>> {

	private static final String USER_CUSTOM_INFORMATION_EMAIL = "email";
	
	private static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	private Pattern pattern;
	
	private Matcher matcher;
	
	public void initialize(ValidateEmailInSet constraintAnnotation) {
		pattern = Pattern.compile(EMAIL_PATTERN);
	}


	public boolean isValid(Map<String, Object> value,ConstraintValidatorContext context) {
		
		if(null!= value && value.containsKey(USER_CUSTOM_INFORMATION_EMAIL)){
			String email = String.valueOf(value.get(USER_CUSTOM_INFORMATION_EMAIL));
			if(null!=email&& !email.trim().isEmpty()&& !email.equals("null")){
				matcher = pattern.matcher(email);
				return matcher.matches();
			}
			
		}
		// Not applies
		return true;
		
	}
}
