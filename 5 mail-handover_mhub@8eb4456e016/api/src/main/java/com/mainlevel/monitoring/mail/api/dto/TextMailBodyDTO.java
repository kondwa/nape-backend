/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.mail.api.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * DTO holding mail content as plain text and html.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class TextMailBodyDTO extends MailBodyDTO {

    private String text;

    private String html;

    private String encoding;

    /**
     * Constructor for TemplateMailBodyDTO.
     */
    public TextMailBodyDTO() {
        super(MailBodyType.TEXT);
    }

}
