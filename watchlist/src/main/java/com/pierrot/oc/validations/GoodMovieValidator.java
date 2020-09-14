package com.pierrot.oc.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.pierrot.oc.entities.WatchlistItem;

public class GoodMovieValidator implements ConstraintValidator<GoodMovie, WatchlistItem> {

	@Override
	public boolean isValid(WatchlistItem value, ConstraintValidatorContext context) {
		double rating = 0 ;
		try {
			rating = (Double.valueOf(value.getRating()));
		} catch (NumberFormatException e) {
			;
		}
		
		return !(rating >= 8 & value.getPriority().startsWith("L"));
	}

}
