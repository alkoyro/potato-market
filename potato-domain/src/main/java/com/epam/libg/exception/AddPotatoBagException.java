package com.epam.libg.exception;

/**
 * exception indicates that adding new {@link com.epam.libg.domain.PotatoBag} failed
 */
public class AddPotatoBagException extends Exception {
    public AddPotatoBagException(String message) {
        super(message);
    }
}
