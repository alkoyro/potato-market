package com.epam.libg.model.validator;

import com.epam.libg.exception.ConvertingException;
import com.epam.libg.model.validator.constraint.PriceConstraint;
import com.epam.libg.util.PriceConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * custom validator to verify if {@link String} can be converted into {@link java.math.BigDecimal} type
 */
public class PriceValidator implements ConstraintValidator<PriceConstraint, String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PriceValidator.class);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        LOGGER.debug("validation price value: " + value);

        if (value != null) {
            try {
                PriceConverter.convert(value);
                return true;
            } catch (ConvertingException e) {
                return false;
            }
        }
        return false;
    }
}
