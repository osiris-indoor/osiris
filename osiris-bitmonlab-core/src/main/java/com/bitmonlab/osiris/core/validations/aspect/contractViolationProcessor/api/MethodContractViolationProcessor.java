package com.bitmonlab.osiris.core.validations.aspect.contractViolationProcessor.api;

import java.util.Collection;

import javax.validation.ConstraintViolation;

/**
 * Interface with the contract for classes that processes the validation errors.
 *
 */
public interface MethodContractViolationProcessor {

	/**
	 * Method called when the parameters are not correct.
	 * @param parametersViolations list of parameters.
	 */
	void processMethodParameterValidation(Collection<ConstraintViolation<Object>> parametersViolations) throws Exception;

	/**
	 * Method called when the return value are not correct.
	 * @param returnsViolations Collection of violations for return value.
	 */
	void processMethodReturnValueValidation(Collection<ConstraintViolation<Object>> returnsViolations) throws Exception;
}