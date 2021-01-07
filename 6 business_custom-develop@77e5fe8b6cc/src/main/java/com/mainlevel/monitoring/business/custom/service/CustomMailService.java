/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.service;

import java.util.List;

import com.mainlevel.monitoring.business.custom.resource.dto.user.CustomSureyParticipantDTO;

/**
 * Service for sending mails.
 */
public interface CustomMailService {

    /**
     * Sends an invitation mail to a single participant.
     *
     * @param participant the participant to invite
     * @param link the survey link
     * @param surveyName name of the survey
     */
    void sendInvitationMail(CustomSureyParticipantDTO participant, String link, String surveyName);

    /**
     * Send an invitation email to the invited participant. All emails will contain the same link.
     *
     * @param participants the participants to invite
     * @param link the survey link
     * @param surveyName name of the survey
     */
    void sendInvitationMail(List<CustomSureyParticipantDTO> participants, String link, String surveyName);

}
