/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mainlevel.monitoring.business.custom.resource.dto.user.CustomSureyParticipantDTO;
import com.mainlevel.monitoring.business.custom.service.CustomMailService;
import com.mainlevel.monitoring.mail.api.dto.MailAddress;
import com.mainlevel.monitoring.mail.api.dto.MailDTO;
import com.mainlevel.monitoring.mail.api.dto.MailHeaderDTO;
import com.mainlevel.monitoring.mail.api.dto.TemplateMailBodyDTO;
import com.mainlevel.monitoring.mail.api.resource.MailResource;

/**
 * Default implementation of {@link CustomMailService}.
 */
@Service
public class CustomMailServiceImpl implements CustomMailService {

    @Value("${mail.invitation.template}")
    private String INVITATION_TEMPLATE;

    @Value("${mail.invitation.subject}")
    private String INVITATION_SUBJECT;

    @Value("${mail.invitation.sender}")
    private String INVITATION_SENDER;

    @Value("${mail.invitation.from}")
    private String INVITATION_FROM;

    @Autowired
    private MailResource mailResource;

    @Override
    public void sendInvitationMail(List<CustomSureyParticipantDTO> participants, String link, String surveyName) {
        for (CustomSureyParticipantDTO participant : participants) {
            sendInvitationMail(participant, link, surveyName);
        }
    }

    @Override
    public void sendInvitationMail(CustomSureyParticipantDTO participant, String link, String surveyName) {
        MailAddress sender = MailAddress.builder().name(INVITATION_SENDER).address(INVITATION_FROM).build();

        List<MailAddress> recipients = new ArrayList<>();
        MailAddress recipient = MailAddress.builder().name(participant.getName()).address(participant.getUsername()).build();
        recipients.add(recipient);

        MailHeaderDTO header = MailHeaderDTO.builder().subject(INVITATION_SUBJECT).from(sender).replyTo(sender).toAdresses(recipients).build();

        TemplateMailBodyDTO body = new TemplateMailBodyDTO();
        body.setTemplateName(INVITATION_TEMPLATE);

        Map<String, String> templateParams = new HashMap<>();
        templateParams.put("name", participant.getName());
        templateParams.put("surveyName", surveyName);
        templateParams.put("invitationText", participant.getInvitationText() != null ? participant.getInvitationText() : "");
        templateParams.put("link", link);
        body.setParameters(templateParams);

        MailDTO mail = MailDTO.builder().header(header).body(body).build();

        mailResource.sendEmail(mail);
    }

}
