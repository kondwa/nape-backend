/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.resource.impl;

import static com.mainlevel.monitoring.common.constant.RoleConstant.ROLE_USER;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mainlevel.monitoring.business.custom.resource.CustomSurveyInstanceResource;
import com.mainlevel.monitoring.business.custom.resource.dto.survey.CustomSurveyInstanceDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.survey.CustomSurveyInstanceListDTO;
import com.mainlevel.monitoring.business.custom.service.CustomSurveyInstanceService;
import com.mainlevel.monitoring.business.custom.service.CustomSurveyService;
import com.mainlevel.monitoring.business.custom.service.CustomSurveyTriggerService;
import com.mainlevel.monitoring.common.service.CollectionConversionService;
import com.mainlevel.monitoring.survey.api.dto.survey.ReportingPeriodDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.ReportingPeriodDataDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.ReportingPeriodOverviewListDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.SurveyDTO;
import com.mainlevel.monitoring.survey.api.resource.ReportingPeriodResource;

import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of {@link CustomSurveyInstanceResource}.
 */
@Slf4j
@RestController
@Secured(ROLE_USER)
public class CustomSurveyInstanceResourceImpl implements CustomSurveyInstanceResource {

    @Autowired
    private ReportingPeriodResource reportingPeriodResource;

    @Autowired
    private CustomSurveyService customSurveyService;

    @Autowired
    private CustomSurveyInstanceService customSurveyInstanceService;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private CollectionConversionService collectionConversionService;

    @Autowired
    private CustomSurveyTriggerService triggerService;

    @Override
    public ResponseEntity<CustomSurveyInstanceListDTO> getSurveyInstances(
        @ApiParam(value = "Reference ID of organizational unit") @RequestParam(name = "unitId", required = false) String unitId,
        @PathVariable(name = "surveyGid", required = true) final Long surveyGid) {

        ResponseEntity<ReportingPeriodOverviewListDTO> response = reportingPeriodResource.getReportingPeriods(unitId, surveyGid);

        List<CustomSurveyInstanceDTO> surveys = collectionConversionService.convert(response.getBody().getOverviews(), CustomSurveyInstanceDTO.class);

        log.info("Loaded {} surveys from survey microservice.", surveys.size());

        CustomSurveyInstanceListDTO dto = CustomSurveyInstanceListDTO.builder().surveys(surveys).build();

        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Integer> getSurveyInstanceCount(
        @ApiParam(value = "Reference ID of organizational unit") @RequestParam(name = "unitId", required = false) String unitId,
        @PathVariable(name = "surveyGid", required = true) final Long surveyGid) {

        ResponseEntity<ReportingPeriodOverviewListDTO> response = reportingPeriodResource.getReportingPeriods(unitId, surveyGid);

        return ResponseEntity.ok(response.getBody().getOverviews().size());
    }

    @Override
    public ResponseEntity<CustomSurveyInstanceDTO> getSurveyInstance(@PathVariable(name = "surveyGid", required = true) final Long surveyGid,
        @PathVariable(name = "surveyInstanceGid", required = true) Long surveyInstanceGid) {

        ResponseEntity<ReportingPeriodDataDTO> response = reportingPeriodResource.getReportingPeriodData(surveyInstanceGid);
        CustomSurveyInstanceDTO surveyInstance = conversionService.convert(response.getBody(), CustomSurveyInstanceDTO.class);

        triggerService.applyAllTriggers(surveyInstance);

        return ResponseEntity.ok(surveyInstance);
    }

    @Override
    public ResponseEntity<CustomSurveyInstanceDTO> createSurveyInstance(@PathVariable(name = "surveyGid", required = true) Long surveyGid,
        @RequestBody CustomSurveyInstanceDTO body, @RequestHeader("x-forwarded-for") String ipAddress) {

        SurveyDTO survey = customSurveyService.loadSurvey(surveyGid, false);

        log.info("Creating new reporting period for survey {} {}.", survey.getSurveyName(), survey.getGraphId());

        ReportingPeriodDTO period = customSurveyInstanceService.createSurveyInstance(ipAddress, survey);

        ResponseEntity<CustomSurveyInstanceDTO> response = getSurveyInstance(surveyGid, period.getGraphId());

        return response;
    }

    @Override
    public ResponseEntity<CustomSurveyInstanceDTO> updateSurveyInstance(@PathVariable(name = "surveyGid", required = true) final Long surveyGid,
        @PathVariable(name = "surveyInstanceGid", required = true) final Long surveyInstanceGid, @RequestBody CustomSurveyInstanceDTO body,
        @RequestHeader("x-forwarded-for") String ipAddress) {

        log.info("Saving reporting period {} for survey {}.", surveyInstanceGid, surveyGid);

        ReportingPeriodDTO period = customSurveyInstanceService.updateSurveyInstance(surveyInstanceGid, ipAddress, body);
        ResponseEntity<CustomSurveyInstanceDTO> response = getSurveyInstance(surveyGid, period.getGraphId());

        return response;
    }
    
    @Override
    public ResponseEntity<Void> updateSurveyInstanceStatus(@PathVariable(name = "surveyGid", required = true) final Long surveyGid,
        @PathVariable(name = "surveyInstanceGid", required = true) final Long surveyInstanceGid, @PathVariable(name = "surveyInstanceStatus", required = true) final String surveyInstanceStatus) {

        log.info("received request for update status for instance {}: {}", surveyInstanceGid, surveyInstanceStatus);
        ResponseEntity<Void> response = reportingPeriodResource.updateReportingPeriod(surveyInstanceGid, surveyInstanceStatus);
        return response; 
    }

    @Override
    public ResponseEntity<Void> deleteSurveyInstance(@PathVariable(name = "surveyGid", required = true) final Long surveyGid,
        @PathVariable(name = "surveyInstanceGid", required = true) final Long surveyInstanceGid) {
        ResponseEntity<Void> response = reportingPeriodResource.deleteReportingPeriod(surveyInstanceGid);
        return response;
    }
}
