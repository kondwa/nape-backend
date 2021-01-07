/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.mail.api.exception;

/**
 * Exception raised when sending an email fails.
 */
public class SendMailException extends RuntimeException {

    private static final long serialVersionUID = -2496001491656829410L;

    private static final String MESSAGE = "Error sending e-mail with subject [%s] to recipients [%s].";

    /**
     * Constructor for SendMailException.
     *
     * @param subject the email subject
     * @param recipients the list of recipients
     */
    public SendMailException(String subject, String recipients) {
        super(String.format(MESSAGE, subject, recipients));
    }
}
