package com.bitmonlab.core.validations.aspect.contractViolationProcessor.defaultImplementation;

import java.util.Collection;

import javax.validation.ConstraintViolation;

import com.bitmonlab.core.validations.aspect.contractViolationProcessor.api.MethodContractViolationProcessor;


/**
 * Class whose responsibility is generate a default response when occurs an error during the validation of the 
 * contract of the method.  
 *
 */
public class DefaultMethodContractViolationProcessor implements MethodContractViolationProcessor {


	public void processMethodParameterValidation(Collection<ConstraintViolation<Object>> parametersViolations) throws Exception{
		if (!parametersViolations.isEmpty()){
			throw new IllegalArgumentException();
		}
	}

	
	public void processMethodReturnValueValidation(Collection<ConstraintViolation<Object>> returnsViolations) {
		if (!returnsViolations.isEmpty()){
			throw new IllegalArgumentException();
		}					
	}
}
