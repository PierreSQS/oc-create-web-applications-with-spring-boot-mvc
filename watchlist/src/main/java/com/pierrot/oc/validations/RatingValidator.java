package com.pierrot.oc.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.pierrot.oc.annotations.Rating;

public class RatingValidator implements ConstraintValidator<Rating, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		double rating = 0;
		try {
			rating = Double.parseDouble(value);
		} catch (NumberFormatException e) {
		} catch (NullPointerException ne) {
			// this is not a validation requirement
			// it is just to prevent breaking tests when rating = null
		}

		return (rating < 10.0 && rating > 1.0);

	}

}
