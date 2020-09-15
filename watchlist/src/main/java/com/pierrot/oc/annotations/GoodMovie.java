/**
 * 
 */
package com.pierrot.oc.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.pierrot.oc.validations.GoodMovieValidator;

@Retention(RUNTIME)
@Target(TYPE)
@Constraint(validatedBy = GoodMovieValidator.class)
/**
 * @author messina
 *
 */
public @interface GoodMovie {
	
	String message() default "If a movie is as good as 8 then priority should be at least M";
	
	Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
