package com.epam.libg.exception;

/**
 * exception to indicate that any field failed to be converted
 */
public class ConvertingException extends Exception {

    public ConvertingException(String message, Throwable cause) {
        super(message, cause);
    }
}
