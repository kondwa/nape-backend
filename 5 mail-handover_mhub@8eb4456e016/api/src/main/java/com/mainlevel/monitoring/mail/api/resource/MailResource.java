/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */
package com.mainlevel.monitoring.mail.api.resource;

import static com.mainlevel.monitoring.mail.api.Mail.SERVICE_NAME;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mainlevel.monitoring.mail.api.dto.MailDTO;

/**
 * Service for sending emails.
 */
@FeignClient(SERVICE_NAME)
public interface MailResource {

    /** Resource Mapping URI */
    final String URI = "/mail";

    /**
     * Sends the given email via the configured email provider.
     *
     * @param mail the e-mail to send
     * @return nothing
     */
    @RequestMapping(value = URI, method = POST, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<Void> sendEmail(MailDTO mail);

}
