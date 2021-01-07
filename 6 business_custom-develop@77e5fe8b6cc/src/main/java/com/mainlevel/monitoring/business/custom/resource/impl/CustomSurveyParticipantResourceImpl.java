/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.resource.impl;

import static com.mainlevel.monitoring.common.constant.RoleConstant.ROLE_USER;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.mainlevel.monitoring.business.custom.resource.CustomSurveyParticipantResource;
import com.mainlevel.monitoring.business.custom.resource.dto.user.CustomSureyParticipantDTO;
import com.mainlevel.monitoring.business.custom.service.CustomLinkService;
import com.mainlevel.monitoring.business.custom.service.CustomMailService;
import com.mainlevel.monitoring.business.custom.service.CustomSurveyInstanceService;
import com.mainlevel.monitoring.business.custom.service.CustomSurveyService;
import com.mainlevel.monitoring.business.custom.service.CustomSurveyUserService;
import com.mainlevel.monitoring.survey.api.dto.SurveyLinkDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.ReportingPeriodDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.SurveyDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.SurveyVisibilityType;
import com.mainlevel.monitoring.survey.api.dto.user.SurveyUserDTO;
import com.mainlevel.monitoring.survey.api.dto.user.SurveyUserParticipationRole;
import com.mainlevel.monitoring.survey.api.dto.user.SurveyUserReportingPeriodParticipationDTO;
import com.mainlevel.monitoring.survey.api.dto.user.SurveyUserType;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of {@link CustomSurveyParticipantResource}.
 */
@Slf4j
@RestController
@Secured(ROLE_USER)
public class CustomSurveyParticipantResourceImpl implements CustomSurveyParticipantResource {

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private CustomLinkService linkService;

    @Autowired
    private CustomSurveyService customSurveyService;

    @Autowired
    private CustomSurveyInstanceService customSurveyInstanceService;

    @Autowired
    private CustomSurveyUserService userService;

    @Autowired
    private CustomMailService mailService;

    @Override
    public ResponseEntity<CustomSureyParticipantDTO> createParticipantForSurvey(@PathVariable("surveyGid") Long surveyGid,
        @RequestBody CustomSureyParticipantDTO customParticipant, @RequestHeader("x-forwarded-proto") String protocol,
        @RequestHeader("x-forwarded-host") String host, @RequestHeader("x-forwarded-for") String ipAddress) {

        SurveyDTO survey = customSurveyService.loadSurvey(surveyGid, false);

        // Create Instance
        ReportingPeriodDTO reportingPeriod = customSurveyInstanceService.createSurveyInstance(ipAddress, survey);

        // Create User
        SurveyUserDTO surveyUser = userService.findSurveyUser(customParticipant.getUsername());

        SurveyUserReportingPeriodParticipationDTO participation = new SurveyUserReportingPeriodParticipationDTO();
        participation.setPeriod(reportingPeriod);
        participation.setRole(SurveyUserParticipationRole.OWNER);

        if (surveyUser == null) {
            log.info("User {} does not exist and will be created.", customParticipant.getUsername());
            surveyUser = conversionService.convert(customParticipant, SurveyUserDTO.class);
            surveyUser.setParticipations(Arrays.asList(participation));
            surveyUser.setUserType(SurveyUserType.TEMPORARY);
            surveyUser = userService.createSurveyUser(surveyUser);
        } else {
            if (surveyUser.getUserType() != SurveyUserType.TEMPORARY) {
                throw new RuntimeException("User already exists in the system and cannot get added as temporary user.");
            }
            surveyUser.getParticipations().add(participation);
            surveyUser = userService.updateSurveyUser(surveyUser);
        }
        customParticipant.setGraphId(surveyUser.getGraphId());

        // Create Link
        SurveyLinkDTO link = SurveyLinkDTO.builder().active(true).user(surveyUser).reportingPeriod(reportingPeriod).survey(survey)
            .visibility(SurveyVisibilityType.PERSONALIZED).expiry(customParticipant.getExpiryDate()).owner(customParticipant.getName()).build();
        link = linkService.saveLink(link);

        if (customParticipant.getSendInvitation()) {
            String completeLink = linkService.createHyperlinkFromLink(protocol, host, link);
            mailService.sendInvitationMail(customParticipant, completeLink, survey.getSurveyName());
        }

        return ResponseEntity.ok(customParticipant);
    }

}
