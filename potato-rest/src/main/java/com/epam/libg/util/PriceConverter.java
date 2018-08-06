package com.epam.libg.util;

import com.epam.libg.exception.ConvertingException;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.text.NumberFormat;

public final class PriceConverter {

    private PriceConverter() {
    }

    public static BigDecimal convert(@NonNull String priceString) throws ConvertingException {
        try {
            return new BigDecimal(priceString);
        } catch (NumberFormatException e) {
            throw new ConvertingException("Error converting price: " + priceString, e);
        }
    }

    public static String convert(@NonNull BigDecimal price) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(price);
    }
}
