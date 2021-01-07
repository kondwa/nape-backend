/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.resource.impl;

import static com.mainlevel.monitoring.common.constant.RoleConstant.ROLE_USER;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mainlevel.monitoring.business.custom.resource.CustomSurveyResource;
import com.mainlevel.monitoring.business.custom.resource.dto.survey.CustomSurveyStatsDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.survey.CustomSurveyStatsListDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.survey.v1.CustomSurveyDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.survey.v1.CustomSurveySubmitDTO;
import com.mainlevel.monitoring.business.custom.service.CustomSurveyService;
import com.mainlevel.monitoring.common.service.CollectionConversionService;
import com.mainlevel.monitoring.survey.api.dto.OrganizationalUnitDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.ParticipantSessionDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.ReportingPeriodDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.ReportingPeriodStatus;
import com.mainlevel.monitoring.survey.api.dto.survey.SurveyDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.SurveyStatsListDTO;

import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * Resource implementationof {@link CustomSurveyResource}.
 */
@SuppressWarnings("deprecation")
@Slf4j
@RestController
@Secured(ROLE_USER)
public class CustomSurveyResourceImpl implements CustomSurveyResource {

    private static final String UNIT_ID = "LE";

    @Autowired
    private CustomSurveyService customSurveyService;

    @Autowired
    private CollectionConversionService collectionConversionService;

    @Override
    public ResponseEntity<CustomSurveyStatsListDTO> getSurveyStats(
        @ApiParam(value = "Reference ID of organizational unit") @RequestParam(name = "unitKey", required = false) String unitKey) {

        SurveyStatsListDTO stats = customSurveyService.loadSurveyStats(unitKey);

        List<CustomSurveyStatsDTO> customStats = collectionConversionService.convert(stats.getSurveys(), CustomSurveyStatsDTO.class);

        log.info("Loaded {} survey stats from survey microservice.", customStats.size());

        CustomSurveyStatsListDTO dto = CustomSurveyStatsListDTO.builder().surveys(customStats).build();

        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<CustomSurveyDTO> submitSurveyInstance(@RequestBody @Valid CustomSurveySubmitDTO body,
        @RequestHeader("x-forwarded-for") String ipAddress) {

        String templateId = body.getTemplateId();
        long templateVersion = body.getTemplateVersion();
        String submissionTimestamp = body.getSubmissionTimestamp();
        String foreignSurveyId = body.getForeignSurveyId();
        String appVersion = body.getAppVersion();

        log.info("Submit new survey for template {} and version {}. Mobile App version {}.", templateId, templateVersion, appVersion);

        traceBody(body);
        
        if(foreignSurveyId != null && customSurveyService.findReportingPeriods(foreignSurveyId)) {
            log.info("ReportingPeriod with foreign survey Id {} already exists", foreignSurveyId);
            return ResponseEntity.ok(CustomSurveyDTO.builder().build());
        }

        SurveyDTO survey = customSurveyService.loadSurvey(templateId, templateVersion);
        
        String unitId = UNIT_ID;
        if(survey.getUnit()!=null && StringUtils.isNotEmpty(survey.getUnit().getForeignId())) {
            unitId = survey.getUnit().getForeignId(); 
        }
        
        log.info("Creating new reporting period for unitId {} and survey {}.", unitId, survey.getTemplateTitle());

        OrganizationalUnitDTO unit = customSurveyService.findUnitByForeignId(unitId);

        Date now = new Date();
        ReportingPeriodDTO period =
            ReportingPeriodDTO.builder().status(ReportingPeriodStatus.SENT).start(now).end(now).survey(survey).unit(unit).submissionTimestamp(submissionTimestamp).foreignSurveyId(foreignSurveyId).build();

        period = customSurveyService.createReportingPeriod(period);

        log.info("Creating new participant session for survey {}.", survey.getTemplateTitle());
        ParticipantSessionDTO session = customSurveyService.createParticipantSession(period, body.getQuestions(), ipAddress);

        log.info("Survey {} successfully submitted with app version {} by user {}.", survey.getTemplateTitle(), appVersion, session.getChangeByUser());

        String unitName = null;
        if (period.getUnit() != null) {
            unitName = period.getUnit().getName();
        }

        CustomSurveyDTO result = CustomSurveyDTO.builder().templateId(templateId).templateVersion(templateVersion).lastEditTime(session.getTime())
            .start(period.getStart()).end(period.getEnd()).lastEditUser(session.getChangeByUser()).templateTitle(survey.getTemplateTitle())
            .status(period.getStatus().name()).unitName(unitName).build();

        return ResponseEntity.ok(result);
    }

    /**
     * Log the JSON input to
     *
     * @param body the body to trace
     * @throws JsonProcessingException
     */
    private void traceBody(Object body) {
        if (log.isDebugEnabled()) {
            if (body != null) {
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    String jsonInString = mapper.writeValueAsString(body);

                    log.info("SUBMIT: " + jsonInString);

                } catch (JsonProcessingException e) {
                    log.warn("Error serializing JSON request.", e);
                }
            }
        }
    }

}
