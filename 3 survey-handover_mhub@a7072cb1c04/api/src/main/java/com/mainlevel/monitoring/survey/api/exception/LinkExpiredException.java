/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.api.exception;

/**
 * Exception raised when a link expired.
 */
public class LinkExpiredException extends RuntimeException {

    private static final long serialVersionUID = 1025868437181179024L;

    private static final String MESSAGE = "Link for token [%s] expired.";

    /**
     * Constructor for LinkExpiredException.
     *
     * @param token the link token
     */
    public LinkExpiredException(String token) {
        super(String.format(MESSAGE, token));
    }

}
