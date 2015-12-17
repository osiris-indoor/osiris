package com.bitmonlab.core.validations.aspect.contractViolationProcessor.defaultImplementation;

import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.bitmonlab.core.validations.annotations.ValidateMap;


public class ValidateMapValidatorImpl implements ConstraintValidator<ValidateMap, Map<String, Object>> {

	public void initialize(ValidateMap constraintAnnotation) {
		////Nothing to do....	
	}

	
	public boolean isValid(Map<String, Object> value,
			ConstraintValidatorContext context) {
		if (value == null) 
			return false;
		
		for(String key: value.keySet()){
			if (StringUtils.isEmpty(key)) return false;
			if (value.get(key) == null) return false;		
		}
		
		return true;
	}


}
