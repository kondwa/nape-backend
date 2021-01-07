/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.mail.api.dto;

/**
 * Type of email content.
 */
public enum MailBodyType {

    /**
     * Mail contains text and html content.
     */
    TEXT,

    /**
     * Mail references a template and contains only parameters.
     */
    TEMPLATE

}
