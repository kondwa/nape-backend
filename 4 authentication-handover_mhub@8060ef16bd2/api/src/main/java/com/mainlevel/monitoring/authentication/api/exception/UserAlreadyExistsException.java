package com.mainlevel.monitoring.authentication.api.exception;

/**
 * Raised when try creating an already existing User.
 */
public class UserAlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor for UserAlreadyExistsException.
     *
     * @param message the error message
     */
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
