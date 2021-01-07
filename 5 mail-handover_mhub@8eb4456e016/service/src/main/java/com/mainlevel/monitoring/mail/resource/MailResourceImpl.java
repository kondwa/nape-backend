/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.mail.resource;

import static com.mainlevel.monitoring.common.constant.RoleConstant.ROLE_USER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mainlevel.monitoring.mail.api.dto.MailDTO;
import com.mainlevel.monitoring.mail.api.resource.MailResource;
import com.mainlevel.monitoring.mail.service.MailService;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of {@link MailResource}.
 */
@Slf4j
@RestController
@Secured(ROLE_USER)
public class MailResourceImpl implements MailResource {

    @Autowired
    private MailService mailService;

    @Override
    public ResponseEntity<Void> sendEmail(@RequestBody MailDTO mail) {

        log.info("Sending mail with subject {}.", mail.getHeader().getSubject());

        mailService.sendMail(mail);

        return ResponseEntity.ok().build();
    }

}
