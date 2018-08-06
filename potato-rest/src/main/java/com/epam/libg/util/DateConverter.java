package com.epam.libg.util;

import com.epam.libg.exception.ConvertingException;
import org.springframework.lang.NonNull;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * util class to convert dates using {@link SimpleDateFormat}
 */
public final class DateConverter {
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat(Constants.CUSTOM_DATE_FORMAT);

    private DateConverter() {
    }

    public static Date convert(@NonNull String dateString) throws ConvertingException {
        try {
            return FORMATTER.parse(dateString);
        } catch (ParseException e) {
            throw new ConvertingException("Error converting date: " + dateString, e);
        }
    }

    public static String convert(@NonNull Date date) {
        return FORMATTER.format(date);
    }
}
