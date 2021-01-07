/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.mail.service;

import com.mainlevel.monitoring.mail.api.dto.MailDTO;

/**
 * Service for sending emails.
 */
public interface MailService {

    /**
     * Sends the given E-Mail.
     *
     * @param mail the email to send
     */
    void sendMail(MailDTO mail);

}
