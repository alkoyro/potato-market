package com.epam.libg.model.validator.constraint;

import com.epam.libg.domain.BagSupplier;
import com.epam.libg.model.validator.BagSupplierValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * constraint can be used on {@link String} fields/setters on request model
 * to verify if it acceptable to be converted into {@link BagSupplier} type
 * See {@link BagSupplierValidator} to get the rules applied by validator
 */
@Documented
@Constraint(validatedBy = BagSupplierValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BagSupplierConstraint {
    String message() default "Wrong Bag Supplier format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
