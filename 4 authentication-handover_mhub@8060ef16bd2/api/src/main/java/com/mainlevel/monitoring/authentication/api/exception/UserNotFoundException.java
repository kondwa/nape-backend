package com.mainlevel.monitoring.authentication.api.exception;

/**
 * Rasied when the requested user does not exist.
 */
public class UserNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor for UserNotFoundException.
     *
     * @param message the error message
     */
    public UserNotFoundException(String message) {
        super(message);
    }

}
