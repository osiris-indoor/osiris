package com.bitmonlab.core.validations.validador.hibernate;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;

import com.bitmonlab.core.validations.validador.api.MethodInvocationValidator;



/**
 * Class used to validate the method invocation.
 */
public class HibernateMethodInvocationValidator implements MethodInvocationValidator {

	/**
	 * The hibernate implementation of method validator.
	 */
	ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	Validator validator = factory.getValidator();
	ExecutableValidator executableValidator = factory.getValidator().forExecutables();
	
	public Collection<ConstraintViolation<Object>> areValidInvocationParameters(Object object,Method method, Object[] parameterValues) {
		Set<ConstraintViolation<Object>> validateBeanConstrains = validator.validate(object);
		for(Object param: parameterValues){
			if(null!=param) validateBeanConstrains.addAll(validator.validate(param));
		}
		validateBeanConstrains.addAll(executableValidator.validateParameters(object, method, parameterValues));
		
		return validateBeanConstrains;
	}
	
	public Collection<ConstraintViolation<Object>> isValidReturnValue(Object object, Method method, Object returnValue) {
		Set<ConstraintViolation<Object>> validateParameters = executableValidator.validateReturnValue(object, method, returnValue);
		return validateParameters;
	}
}
