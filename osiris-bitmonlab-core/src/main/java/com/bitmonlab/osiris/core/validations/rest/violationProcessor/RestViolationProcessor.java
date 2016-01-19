package com.bitmonlab.osiris.core.validations.rest.violationProcessor;

import java.util.Collection;

import javax.validation.ConstraintViolation;

import com.bitmonlab.osiris.core.validations.aspect.contractViolationProcessor.api.MethodContractViolationProcessor;


/**
 *
 * Class that process the violations in the rest layer.
 */
public class RestViolationProcessor implements MethodContractViolationProcessor{

	
	public void processMethodParameterValidation(Collection<ConstraintViolation<Object>> violations) throws Exception {
		String constraintMessage =  "";
		if (!violations.isEmpty()){
			try {
				for (ConstraintViolation<Object> constraintViolation : violations) {
					if(!(constraintViolation.getInvalidValue() instanceof Collection)){
						constraintMessage += constraintViolation.getMessage() + " Value:" +constraintViolation.getInvalidValue() + " ,";
					}else{
						constraintMessage += constraintViolation.getMessage() + " ,";
					}
				}
				constraintMessage = constraintMessage.substring(0, constraintMessage.length() - 2);
			} catch (Throwable e) {// I don't trust it works in every case
				e.printStackTrace();
			}
			throw new InvalidParametersException(constraintMessage);
		}
	}

	
	public void processMethodReturnValueValidation(Collection<ConstraintViolation<Object>> violations) throws Exception {
	}
}
