package com.pierrot.oc.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PriorityValidator implements ConstraintValidator<Priority, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		// to avoid breaking test, not a validation requirement		
		if (value == null) {
			return false;
		}
		return value.length() == 1 & "HLM".contains(value);
	}

}
