package com.pierrot.oc.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.pierrot.oc.entities.WatchlistItem;

public class GoodMovieValidator implements ConstraintValidator<GoodMovie, WatchlistItem> {

	@Override
	public boolean isValid(WatchlistItem value, ConstraintValidatorContext context) {
		if (value.getRating() == null ||value.getRating().isEmpty()) {
			value.setRating("5.1");
		}
		return !(Double.valueOf(value.getRating()) >= 8 && value.getPriority().startsWith("L"));
	}

}
