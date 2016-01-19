package com.bitmonlab.osiris.core.validations.aspect;

import java.lang.reflect.Method;

import javax.inject.Named;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import com.bitmonlab.osiris.core.validations.annotations.ValidationRequired;
import com.bitmonlab.osiris.core.validations.aspect.contractViolationProcessor.api.MethodContractViolationProcessor;
import com.bitmonlab.osiris.core.validations.validador.api.MethodInvocationValidator;


/**
 * Aspect use to validate the invocation of the contract of the method.
 */
@Aspect
@Named
public class MethodInvocationValidationAspect {
	
	/**
	 * Method that execute around a method in order to execute the validations. 
	 *
	 * @param joinPoint the joint point that execute.
	 * @return Object return of the execution.
	 * @throws Throwable exception when a problem occurs in the invocation of the contract.
	 */
	@Around("execution(@com.bitmonlab.core.commons.validations.annotations.ValidationRequired public * *.*(..))")
	public Object validateMethodContract(ProceedingJoinPoint joinPoint)	throws Throwable {
		validateMethodParameters(joinPoint);
		
		Object result = executeMethod(joinPoint);
		
		validateReturnValue(joinPoint,result);
		
		return result;
	}
	
	/**
	 * Method that execute the method.
	 * @param joinPoint the joint point.
	 * @return the result of the method execution.
	 * @throws Throwable exceptions generate by the method.
	 */
	private Object executeMethod(ProceedingJoinPoint joinPoint)	throws Throwable {
		return joinPoint.proceed();
	}

	/**
	 * Method that validate if the return value.
	 * @param joinPoint the joint point of the method.
	 * @param returnValue the result value.
	 * @throws Exception the exception throwing when a problem occurs.
	 */
	private void validateReturnValue(ProceedingJoinPoint joinPoint,Object returnValue) throws Exception {
		Method method = retreiveCallMethod(joinPoint);
		MethodContractViolationProcessor violationProcessor = retrieveViotationProcessor(method);		
		
		MethodInvocationValidator validator = retrieveMethodValidator(method);	
		
		violationProcessor.processMethodReturnValueValidation(
				validator.isValidReturnValue(joinPoint.getTarget(), method, returnValue));
	}

	/**
	 * Method that retrieve the calling method.
	 * @param joinPoint the joint point.
	 * @return the method called method.
	 */
	private Method retreiveCallMethod(ProceedingJoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		return signature.getMethod();
	}
	
	/**
	 * Method that validate the input parameters of a method.
	 * @param joinPoint the method joint point.
	 * @throws Exception the exception throwing when a problem occurs.
	 */
	private void validateMethodParameters(ProceedingJoinPoint joinPoint) throws Exception {
		Method method = retreiveCallMethod(joinPoint);
		
		MethodContractViolationProcessor violationProcessor = retrieveViotationProcessor(method);		
		MethodInvocationValidator validator = retrieveMethodValidator(method);	
		
		violationProcessor.processMethodParameterValidation(
				validator.areValidInvocationParameters(joinPoint.getTarget(),method, joinPoint.getArgs()));
	}
	
	/**
	 * Method that return the processor defined in the method annotation.
	 * @param method the annotated method.
	 * @return the constraints violation processor.
	 * @throws InstantiationException exception if it's not possible instantiate the processor. 
	 * @throws IllegalAccessException if it's not possible access to processor.
	 */
	private MethodContractViolationProcessor retrieveViotationProcessor(Method method)	throws InstantiationException, IllegalAccessException {
		ValidationRequired annotation = method.getAnnotation(ValidationRequired.class);
		Class<? extends MethodContractViolationProcessor> processorClass = annotation.processor();	
		return processorClass.newInstance();
	}
	
	/**
	 * Method that retrieve the validator indicates in the annotation.
	 * @param method the method that is execute.
	 * @return The method validator.
	 * @throws InstantiationException the exception in the instantiation.
	 * @throws IllegalAccessException the exception is has been an illegal access.
	 */
	MethodInvocationValidator retrieveMethodValidator(Method method)
			throws InstantiationException, IllegalAccessException {
		ValidationRequired annotation = method.getAnnotation(ValidationRequired.class);
		Class<? extends MethodInvocationValidator> validatorClass = annotation.validator();
		return validatorClass.newInstance();
	}	
}
