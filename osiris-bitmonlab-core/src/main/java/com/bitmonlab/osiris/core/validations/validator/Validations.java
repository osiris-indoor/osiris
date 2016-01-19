package com.bitmonlab.osiris.core.validations.validator;

import java.util.Collection;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Named;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.bitmonlab.osiris.core.validations.rest.violationProcessor.InvalidParametersException;

@Named
public class Validations {
	
	private static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	public void checkIsNotNull(Object... objects){
		for(Object object: objects){
			if(object==null){
				throw new InvalidParametersException("Passed parameter can not be null");
			}
		}
	}
	
	public void checkIsNotNullAndNotBlank(String... objects){
		checkIsNotNull((Object[])objects);
		for(String object: objects){
			object=object.trim();
			if(object.length()==0){
				throw new InvalidParametersException("Passed parameter can not be blank");
			}
		}
	}
	
	public void checkIsEmail(String... objects){
		checkIsNotNullAndNotBlank(objects);
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		for(String object: objects){
			Matcher matcher = pattern.matcher(object);
			boolean isEmail=matcher.matches();
			if(!isEmail){
				throw new InvalidParametersException("Passed parameter with value "+ object +" must be an email");
			}
		}
	}
	
	public void checkMin(Integer minValue,Integer...objects){
		checkIsNotNull((Object[])objects);
		for(Integer object: objects){
			if(object<minValue){
				throw new InvalidParametersException("Passed parameter with value "+ object +" is less than "+minValue);
			}
		}
	}
	
	public void checkMin(Double minValue,Double...objects){
		checkIsNotNull((Object[])objects);
		for(Double object: objects){
			if(object<minValue){
				throw new InvalidParametersException("Passed parameter with value "+ object +" is less than "+minValue);
			}
		}
	}
	
	public void checkMax(Double maxValue,Double...objects){
		checkIsNotNull((Object[])objects);
		for(Double object: objects){
			if(object>maxValue){
				throw new InvalidParametersException("Passed parameter with value "+ object +"is greater than "+maxValue);
			}
		}
	}
	
	public void checkCollection(Collection<?> collection){
		checkIsNotNull(collection);
		checkCollectionIsNotEmpty(collection);
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		for(Object object:collection){
			Set<ConstraintViolation<Object>> validation = validator.validate(object);
			if (validation.size() > 0) {
				throw new InvalidParametersException("Passed collection is not correct");
			}
		}
	}
	
	private void checkCollectionIsNotEmpty(Collection<?> collection){
		boolean isEmpty=collection.isEmpty();
		if(isEmpty){
			throw new InvalidParametersException("Passed collection can not be empty");
		}
	}

}
