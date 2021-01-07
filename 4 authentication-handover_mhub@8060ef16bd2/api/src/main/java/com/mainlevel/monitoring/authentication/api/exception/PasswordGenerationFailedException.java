package com.mainlevel.monitoring.authentication.api.exception;

/**
 * Exception raised when a user password cannot get hashed.
 */
public class PasswordGenerationFailedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor for PasswordGenerationFailedException.
     *
     * @param message the error message
     * @param throwable the causing error
     */
    public PasswordGenerationFailedException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
