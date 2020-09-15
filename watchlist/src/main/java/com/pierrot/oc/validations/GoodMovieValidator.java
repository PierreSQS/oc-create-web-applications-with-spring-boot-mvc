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
			;
		}
		
		String priority = value.getPriority();
		return (priority == null) || !(rating >= 8 & priority.startsWith("L"));
	}

}
