package com.bitmonlab.core.validations.rest.violationProcessor;

/**
 * Class that represents when a constraints defined for a parameter is violated. Is a runtime exception because its use by an aspect
 * and we don't want to declare it in all methods that the parameter weaves.  
 */
public class InvalidParametersException extends RuntimeException{

	private static final long serialVersionUID = 1654047973000920698L;

	public InvalidParametersException(String description) {
		super(description);
	}
}
