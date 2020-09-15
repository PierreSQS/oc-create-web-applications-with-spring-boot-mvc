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
		}  catch (NullPointerException e) {
			// to avoid breaking test, not a validation requirement
		}
		
		String priority = value.getPriority();
		// the verificaton of null is not a validation requirement
		// it is just to prevent breaking test
		return (priority == null) || !(rating >= 8 & priority.equals("L"));
	}

}
