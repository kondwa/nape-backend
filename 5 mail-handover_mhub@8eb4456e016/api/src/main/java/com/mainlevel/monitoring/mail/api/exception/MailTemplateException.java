/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.mail.api.exception;

/**
 * Exception raised when sending an email fails.
 */
public class MailTemplateException extends RuntimeException {

    private static final long serialVersionUID = -2496001491656829410L;

    private static final String MESSAGE = "Error loading e-mail template with name [%s].";

    /**
     * Constructor for SendMailException.
     *
     * @param templateName the email template name
     */
    public MailTemplateException(String templateName) {
        super(String.format(MESSAGE, templateName));
    }
}
