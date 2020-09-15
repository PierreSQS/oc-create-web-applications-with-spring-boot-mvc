package com.pierrot.oc.validations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Retention(RUNTIME)
@Target({ TYPE, FIELD })
@Constraint(validatedBy = RatingValidator.class)
public @interface Rating {
	String message() default "Rating should be a number between 1.0-10.0. Decimals with (.) are accepted";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
