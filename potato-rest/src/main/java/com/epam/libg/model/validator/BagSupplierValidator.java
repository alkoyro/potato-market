package com.epam.libg.model.validator;

import com.epam.libg.domain.BagSupplier;
import com.epam.libg.model.validator.constraint.BagSupplierConstraint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * custom validator to verify if {@link String} can be converted into {@link BagSupplier} type
 */
public class BagSupplierValidator implements ConstraintValidator<BagSupplierConstraint, String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BagSupplierValidator.class);

    private static final String ERROR_DETAIL = "Expected bag supplier types: (%s)";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean isValid = Optional.ofNullable(BagSupplier.findBySupplierName(value)).isPresent();

        LOGGER.debug("validation bagSupplier value: " + value + " result: " + isValid);
        if (!isValid) {
            context.disableDefaultConstraintViolation();

            String supplierTypes = Stream.of(BagSupplier.values()).
                    map(BagSupplier::getSupplierName).
                    collect(Collectors.joining(","));

            String errorDetail = String.format(ERROR_DETAIL, supplierTypes);

            context.buildConstraintViolationWithTemplate(errorDetail).addConstraintViolation();
        }

        return isValid;

    }
}
