package com.bitmonlab.core.validations.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.bitmonlab.core.validations.aspect.contractViolationProcessor.defaultImplementation.ValidateEmailInSetImpl;


@Target( { FIELD, PARAMETER })
@Retention(RUNTIME)
@Constraint(validatedBy = ValidateEmailInSetImpl.class)
@Documented
public @interface ValidateEmailInSet {

	   String message() default "Wrong email format!";

	    Class<?>[] groups() default {};

	    Class<? extends Payload>[] payload() default {};
}
