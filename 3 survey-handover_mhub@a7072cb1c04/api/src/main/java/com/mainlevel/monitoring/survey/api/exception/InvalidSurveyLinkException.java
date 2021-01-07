/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.api.exception;

/**
 * Exception raised when a link is not valid.
 */
public class InvalidSurveyLinkException extends RuntimeException {

    private static final String MESSAGE = "Link for user [%s]  cannot be created. ";

    private static final long serialVersionUID = -828478937748063692L;

    /**
     * Constructor for InvalidSurveyLinkException.
     *
     * @param username username the link is created for
     * @param message the error message
     */
    public InvalidSurveyLinkException(String username, String message) {
        super(String.format(MESSAGE, username) + message);
    }

}
