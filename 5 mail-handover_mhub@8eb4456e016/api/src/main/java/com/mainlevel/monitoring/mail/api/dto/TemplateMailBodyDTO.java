/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */
package com.mainlevel.monitoring.mail.api.dto;

import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * DTO holding mail content as reference to a template including parameters.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class TemplateMailBodyDTO extends MailBodyDTO {

    private String templateName;

    private Map<String, String> parameters;

    /**
     * Constructor for TemplateMailBodyDTO.
     */
    public TemplateMailBodyDTO() {
        super(MailBodyType.TEMPLATE);
    }
}
