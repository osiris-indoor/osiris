package com.bitmonlab.osiris.core.validations.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.bitmonlab.osiris.core.validations.aspect.contractViolationProcessor.api.MethodContractViolationProcessor;
import com.bitmonlab.osiris.core.validations.aspect.contractViolationProcessor.defaultImplementation.DefaultMethodContractViolationProcessor;
import com.bitmonlab.osiris.core.validations.validador.api.MethodInvocationValidator;
import com.bitmonlab.osiris.core.validations.validador.hibernate.HibernateMethodInvocationValidator;


/**
 * Annotation to mark the method to be validated.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidationRequired {
	
	/**
	 * Parameter of the annotation that defines which processor execute the aspect.
	 * It's an optional parameter with DefaultMethodContractViolationProcessor by default.
	 * @return the processor.
	 */
	Class<? extends MethodContractViolationProcessor> processor() default DefaultMethodContractViolationProcessor.class;
	
	/**
	 * Parameter of the annotation that defines which validator execute the aspect.
	 * It's an optional parameter with HibernateMethodInvocationValidator by default.
	 * @return the class that makes the method validations.
	 */
	Class<? extends MethodInvocationValidator> validator() default HibernateMethodInvocationValidator.class;
}
