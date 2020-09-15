package com.pierrot.oc.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RatingValidator implements ConstraintValidator<Rating, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		double rating = 0;
		try {
			rating = Double.parseDouble(value);
		} catch (NumberFormatException e) {
		} catch (NullPointerException ne) {
			
		}

		return (rating < 10.0 && rating > 1.0);

	}

}
