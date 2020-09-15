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
@Constraint(validatedBy = PriorityValidator.class)
public @interface Priority {
	String message() default "Please enter M,L or H for priority";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
