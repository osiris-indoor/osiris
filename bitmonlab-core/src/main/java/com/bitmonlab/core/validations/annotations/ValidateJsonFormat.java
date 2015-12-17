package com.bitmonlab.core.validations.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.bitmonlab.core.validations.aspect.contractViolationProcessor.defaultImplementation.ValidateJsonFormatImpl;


@Target( { FIELD, PARAMETER })
@Retention(RUNTIME)
@Constraint(validatedBy = ValidateJsonFormatImpl.class)
@Documented
public @interface ValidateJsonFormat {

	   String message() default "Invaild JSON format!";

	    Class<?>[] groups() default {};

	    Class<? extends Payload>[] payload() default {};
}
