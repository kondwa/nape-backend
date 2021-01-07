/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.resource;

import static com.mainlevel.monitoring.common.constant.RoleConstant.ROLE_ANONYMOUS;
import static com.mainlevel.monitoring.common.constant.RoleConstant.ROLE_USER;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mainlevel.monitoring.common.service.CollectionConversionService;
import com.mainlevel.monitoring.survey.api.dto.survey.ParticipantSessionDTO;
import com.mainlevel.monitoring.survey.api.resource.ParticipantSessionResource;
import com.mainlevel.monitoring.survey.database.node.Answer;
import com.mainlevel.monitoring.survey.database.node.Client;
import com.mainlevel.monitoring.survey.database.node.ParticipantSession;
import com.mainlevel.monitoring.survey.database.node.ReportingPeriod;
import com.mainlevel.monitoring.survey.service.ParticipantSessionService;
import com.mainlevel.monitoring.survey.service.ReportingPeriodService;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of {@link ParticipantSessionResource}.
 */
@Slf4j
@RestController
@Secured(ROLE_USER)
public class ParticipantSessionResourceImpl implements ParticipantSessionResource {

    @Autowired
    private ParticipantSessionService participantSessionService;

    @Autowired
    private ReportingPeriodService reportingPeriodService;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private CollectionConversionService collectionConversionService;

    @Override
    @Secured({ROLE_USER, ROLE_ANONYMOUS})
    public ResponseEntity<ParticipantSessionDTO> saveSession(@RequestBody ParticipantSessionDTO sessionDTO) {

        log.info("Creating new Participant Session for Reporting Period {}.", sessionDTO.getReportingPeriod().getGraphId());

        Client client = null;
        if (sessionDTO.getClient() != null) {
            client = conversionService.convert(sessionDTO.getClient(), Client.class);
        }

        List<Answer> answers = collectionConversionService.convert(sessionDTO.getAnswers(), Answer.class);

        ReportingPeriod rp = reportingPeriodService.loadReportingPeriod(sessionDTO.getReportingPeriod().getGraphId());

        ParticipantSession session = participantSessionService.createParticipantSession(rp, answers);
        session.setClient(client);

        session = participantSessionService.saveParticipantSession(session);
        ParticipantSessionDTO result = conversionService.convert(session, ParticipantSessionDTO.class);

        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<ParticipantSessionDTO> getLatestSession(
        @PathVariable(name = "reportingPeriodGid", required = true) Long reportingPeriodGid) {

        ParticipantSession session = participantSessionService.getLatestParticipantSession(reportingPeriodGid);
        ParticipantSessionDTO result = conversionService.convert(session, ParticipantSessionDTO.class);

        return ResponseEntity.ok(result);
    }

}
