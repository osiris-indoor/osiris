package com.bitmonlab.core.validations.aspect.contractViolationProcessor.defaultImplementation;

import java.util.Collection;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.bitmonlab.core.validations.annotations.NotNullInCollection;


@SuppressWarnings("rawtypes")
public class NotNullInCollectionValidatorImpl implements
		ConstraintValidator<NotNullInCollection, Collection> {

	public void initialize(NotNullInCollection constraintAnnotation) {
		// //Nothing to do....
	}

	public boolean isValid(Collection values,
			ConstraintValidatorContext context) {

		if (values == null){
			return false;
		}
		
		for (Object value : values) {
			if (value == null){
				return false;
			}
		}

		return true;
	}

}
