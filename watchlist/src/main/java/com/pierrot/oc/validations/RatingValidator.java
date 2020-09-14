package com.pierrot.oc.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.pierrot.oc.entities.WatchlistItem;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RatingValidator implements ConstraintValidator<Rating, WatchlistItem> {

	@Override
	public boolean isValid(WatchlistItem value, ConstraintValidatorContext context) {
		
		String ratingStr = value.getRating();

		try {
			double rating = Double.parseDouble(ratingStr);
			if (rating < 10.0 && rating > 1.0) {
				return true;
			}
		} catch (NumberFormatException e) {
			log.debug("the Rating-Validation "+ratingStr+ "went wrong");
		}
		return false;

	}

}
