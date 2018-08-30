package com.epam.libg.model;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * represents the field expected representation (if request fails to be translated)
 * see {@link com.epam.libg.exception.handling.DefaultExceptionHandler} how it used
 */
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExpectedTypeFormat {
    /**
     * description of the field
     *
     * @return description
     */
    String value();

    /**
     * example of usage
     *
     * @return example
     */
    String example();
}
