package com.bitmonlab.core.validations.aspect.contractViolationProcessor.defaultImplementation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.json.JSONException;
import org.json.JSONObject;

import com.bitmonlab.core.validations.annotations.ValidateJsonFormat;


public class ValidateJsonFormatImpl implements ConstraintValidator<ValidateJsonFormat, String> {


	
	public void initialize(ValidateJsonFormat constraintAnnotation) { }

	
	public boolean isValid(String value,ConstraintValidatorContext context) {
		boolean isValid = false;
		try {
	        new JSONObject(value);
	        isValid = true;
	    } catch(JSONException ex) { 
           //by default isValid is False
	    	if(value.isEmpty()){
	    		// we allow empty input as a valid JSON object
	    		isValid = true;
	    	}
	    }
		
		return isValid;
	}
}
