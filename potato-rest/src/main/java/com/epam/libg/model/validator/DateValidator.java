package com.epam.libg.model.validator;

import com.epam.libg.exception.ConvertingException;
import com.epam.libg.model.validator.constraint.DateConstraint;
import com.epam.libg.util.DateConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * custom validator to verify if {@link String} can be converted into {@link java.util.Date} type
 */
public class DateValidator implements ConstraintValidator<DateConstraint, String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DateValidator.class);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        LOGGER.debug("validation date value: " + value);

        if (value != null) {
            try {
                DateConverter.convert(value);
                return true;
            } catch (ConvertingException e) {
                return false;
            }
        }
        return false;
    }
}
