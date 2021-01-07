/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.api.exception;

/**
 * Raised when the required survey-admin project does not exist.
 */
public class MissingProjectException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor for MissingProjectException.
     *
     * @param message the error message
     */
    public MissingProjectException(String message) {
        super(message);
    }

    /**
     * Constructor for MissingProjectException.
     *
     * @param message the error message
     * @param throwable the causing error
     */
    public MissingProjectException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
