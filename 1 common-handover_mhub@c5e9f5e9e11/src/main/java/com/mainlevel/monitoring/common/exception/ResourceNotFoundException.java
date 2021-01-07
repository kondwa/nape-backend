package com.mainlevel.monitoring.common.exception;

/**
 * Exception called when a resource is not found.
 */
public class ResourceNotFoundException extends RuntimeException {

    /** Default serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor for ResourceNotFoundException.
     *
     * @param message error message
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

}
