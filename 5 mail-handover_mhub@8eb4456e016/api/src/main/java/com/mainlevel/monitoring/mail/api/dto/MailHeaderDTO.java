/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.mail.api.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO holding the headers of an email.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class MailHeaderDTO {

    private String subject;

    private MailAddress from;

    private MailAddress replyTo;

    private List<MailAddress> toAdresses;

    private List<MailAddress> ccAdresses;

    private List<MailAddress> bccAdresses;

}
