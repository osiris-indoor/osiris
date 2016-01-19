package com.bitmonlab.core.validations.validador.api;

import java.lang.reflect.Method;
import java.util.Collection;

import javax.validation.ConstraintViolation;

/**
 * Interface for the classes that implements the validations. 
 *
 */
public interface MethodInvocationValidator {

	/**
	 * Method that validate the invocation of a method.
	 * @param object the object that method is invoke.
	 * @param method the method that is invoke.
	 * @param returnValue the return value to check.
	 * @return a collection of constraint violations.
	 */
	Collection<ConstraintViolation<Object>> isValidReturnValue(Object object, Method method,
			Object returnValue);

	/**
	 * Method that validate the return value of the invocation.
	 * @param object the invocation object. 
	 * @param method the method that has been invoked.
	 * @param parameterValues the value for parameters. 
	 * @return a collection of constraint violations.
	 */
	Collection<ConstraintViolation<Object>> areValidInvocationParameters(Object object,
			Method method, Object[] parameterValues);
}