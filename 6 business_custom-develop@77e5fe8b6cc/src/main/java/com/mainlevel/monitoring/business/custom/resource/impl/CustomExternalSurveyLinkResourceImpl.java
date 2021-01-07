/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.resource.impl;

import static com.mainlevel.monitoring.common.constant.RoleConstant.ROLE_ANONYMOUS;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.mainlevel.monitoring.business.custom.resource.CustomExternalSurveyLinkResource;
import com.mainlevel.monitoring.business.custom.resource.dto.survey.CustomSurveyInstanceDTO;
import com.mainlevel.monitoring.business.custom.service.CustomSurveyInstanceService;
import com.mainlevel.monitoring.business.custom.service.CustomSurveyService;
import com.mainlevel.monitoring.business.custom.service.CustomSurveyTriggerService;
import com.mainlevel.monitoring.survey.api.dto.ClientDTO;
import com.mainlevel.monitoring.survey.api.dto.ClientType;
import com.mainlevel.monitoring.survey.api.dto.SurveyLinkDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.ReportingPeriodDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.ReportingPeriodDataDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.ReportingPeriodStatus;
import com.mainlevel.monitoring.survey.api.dto.survey.SurveyDTO;
import com.mainlevel.monitoring.survey.api.resource.ReportingPeriodResource;
import com.mainlevel.monitoring.survey.api.resource.SurveyLinkResource;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of {@link CustomExternalSurveyLinkResource}.
 */
@Slf4j
@RestController
@Secured(ROLE_ANONYMOUS)
public class CustomExternalSurveyLinkResourceImpl implements CustomExternalSurveyLinkResource {

    @Autowired
    private SurveyLinkResource linkResource;

    @Autowired
    private ReportingPeriodResource reportingPeriodResource;

    @Autowired
    private CustomSurveyService customSurveyService;

    @Autowired
    private CustomSurveyInstanceService customSurveyInstanceService;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private CustomSurveyTriggerService triggerService;

    @Override
    public ResponseEntity<CustomSurveyInstanceDTO> getSurveyInstance(@PathVariable(name = "linkToken", required = true) String linkToken,
        @RequestHeader("x-forwarded-for") String ipAddress) {

        log.info("Requesting survey instance for token {} from IP {}.", linkToken, ipAddress);

        SurveyLinkDTO link = getLink(linkToken);

        ReportingPeriodDTO reportingPeriod;

        switch (link.getVisibility()) {

            case ANONYMOUS:
                if (link.getSurvey() == null) {
                    throw new RuntimeException("Link for token " + linkToken + "does not point to a valid survey.");
                }

                SurveyDTO survey = link.getSurvey();
                ClientDTO client = ClientDTO.builder().address(ipAddress).type(ClientType.WEB).build();

                Date now = new Date();
                ReportingPeriodDTO period =
                    ReportingPeriodDTO.builder().status(ReportingPeriodStatus.NEW).start(now).end(now).survey(survey).client(client).build();

                reportingPeriod = customSurveyService.createReportingPeriod(period);
                break;

            case PERSONALIZED:
                if (link.getReportingPeriod() == null) {
                    throw new RuntimeException("Link for token " + linkToken + "does not point to a valid survey instance.");
                }

                reportingPeriod = link.getReportingPeriod();
                break;

            default: {
                throw new IllegalStateException("Visibility type " + link.getVisibility() + " in link '" + link.getToken() + "' is not supported.");
            }

        }

        Long surveyInstanceGid = reportingPeriod.getGraphId();

        ResponseEntity<ReportingPeriodDataDTO> response = reportingPeriodResource.getReportingPeriodData(surveyInstanceGid);
        CustomSurveyInstanceDTO surveyInstance = conversionService.convert(response.getBody(), CustomSurveyInstanceDTO.class);

        triggerService.applyAllTriggers(surveyInstance);

        return ResponseEntity.ok(surveyInstance);
    }

    @Override
    public ResponseEntity<CustomSurveyInstanceDTO> updateSurveyInstance(@PathVariable(name = "linkToken", required = true) String linkToken,
        @RequestBody CustomSurveyInstanceDTO surveyInstance, @RequestHeader("x-forwarded-for") String ipAddress) {

        log.info("Updating survey instance for token {} from IP {}.", linkToken, ipAddress);

        SurveyLinkDTO link = getLink(linkToken);

        Long surveyInstanceGid;

        switch (link.getVisibility()) {

            case ANONYMOUS:
                if (link.getSurvey() == null) {
                    throw new RuntimeException("Link for token " + linkToken + "does not point to a valid survey.");
                }

                surveyInstanceGid = surveyInstance.getInstanceId();

                break;

            case PERSONALIZED:
                if (link.getReportingPeriod() == null) {
                    throw new RuntimeException("Link for token " + linkToken + "does not point to a valid survey instance.");
                }

                ReportingPeriodDTO reportingPeriod = link.getReportingPeriod();
                surveyInstanceGid = reportingPeriod.getGraphId();

                break;

            default: {
                throw new IllegalStateException("Visibility type " + link.getVisibility() + " in link '" + link.getToken() + "' is not supported.");
            }
        }

        customSurveyInstanceService.updateSurveyInstance(surveyInstanceGid, ipAddress, surveyInstance);

        ResponseEntity<CustomSurveyInstanceDTO> response = getSurveyInstance(linkToken, ipAddress);

        return response;
    }

    /**
     * Load the link DTO for the token.
     *
     * @param token the link token
     * @return the link DTO
     */
    private SurveyLinkDTO getLink(String token) {
        ResponseEntity<SurveyLinkDTO> response = linkResource.getLink(token);

        if (response == null || response.getBody() == null) {
            throw new RuntimeException("Error loading survey link for token '" + token + "'.");
        }

        return response.getBody();
    }

}
