package com.epam.libg.model.validator.constraint;

import com.epam.libg.model.validator.DateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * constraint can be used on {@link String} fields/setters on request model
 * to verify if it acceptable to be converted into {@link java.util.Date} type
 * See {@link DateValidator} to get the rules applied by validator
 */
@Documented
@Constraint(validatedBy = DateValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateConstraint {
    String message() default "Wrong Date format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
