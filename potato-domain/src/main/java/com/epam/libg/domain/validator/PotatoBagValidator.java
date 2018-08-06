package com.epam.libg.domain.validator;

import com.epam.libg.domain.PotatoBag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * simple validator for potato bags
 * should be used in service layer before saving into repo
 */
@Component
public class PotatoBagValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(PotatoBagValidator.class);

    /**
     * checks if all {@link PotatoBag} values are correct
     *
     * @param potatoBag to validate
     * @return if all required fields are valid
     */
    public boolean validate(@NonNull PotatoBag potatoBag) {

        if (potatoBag.getPackedDate() == null) {
            LOGGER.debug("packedDate is null");
            return false;
        }

        if (potatoBag.getBagSupplier() == null) {
            LOGGER.debug("bagSupplier is null");
            return false;
        }

        if (potatoBag.getPotatosNumber() == null) {
            LOGGER.debug("potatosNumber is null");
            return false;
        }

        if (potatoBag.getPrice() == null) {
            LOGGER.debug("price is null");
            return false;
        }

        return true;
    }
}
