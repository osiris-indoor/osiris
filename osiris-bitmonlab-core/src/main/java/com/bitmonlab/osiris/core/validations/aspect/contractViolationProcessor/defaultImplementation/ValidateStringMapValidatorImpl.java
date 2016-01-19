package com.bitmonlab.osiris.core.validations.aspect.contractViolationProcessor.defaultImplementation;

import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.bitmonlab.osiris.core.validations.annotations.ValidateStringMap;


public class ValidateStringMapValidatorImpl implements ConstraintValidator<ValidateStringMap, Map<String, String>> {


	public void initialize(ValidateStringMap constraintAnnotation) {
		////Nothing to do....	
	}

	
	public boolean isValid(Map<String, String> value,
			ConstraintValidatorContext context) {
		
		for(String key: value.keySet()){
			if (StringUtils.isEmpty(key)) return false;
			if (StringUtils.isEmpty(value.get(key))) return false;		
		}
		
		return true;
	}


}
