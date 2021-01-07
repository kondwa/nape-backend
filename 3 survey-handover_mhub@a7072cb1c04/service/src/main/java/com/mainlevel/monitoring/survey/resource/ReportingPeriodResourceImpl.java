/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.resource;

import static com.mainlevel.monitoring.common.constant.RoleConstant.ROLE_ANONYMOUS;
import static com.mainlevel.monitoring.common.constant.RoleConstant.ROLE_USER;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mainlevel.monitoring.common.monitoring.annotation.PerformanceMonitor;
import com.mainlevel.monitoring.common.service.AuthenticationAccessService;
import com.mainlevel.monitoring.common.service.CollectionConversionService;
import com.mainlevel.monitoring.survey.api.dto.survey.ReportingPeriodDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.ReportingPeriodDataDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.ReportingPeriodOverviewDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.ReportingPeriodOverviewListDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.ReportingPeriodStatus;
import com.mainlevel.monitoring.survey.api.resource.ReportingPeriodResource;
import com.mainlevel.monitoring.survey.database.node.ReportingPeriod;
import com.mainlevel.monitoring.survey.database.queryresult.ReportingPeriodDetailQueryResult;
import com.mainlevel.monitoring.survey.database.queryresult.ReportingPeriodOverview;
import com.mainlevel.monitoring.survey.service.ReportingPeriodService;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of {@link ReportingPeriodResource}.
 */
@Slf4j
@RestController
@Secured(ROLE_USER)
public class ReportingPeriodResourceImpl implements ReportingPeriodResource {

    @Autowired
    private ReportingPeriodService reportingPeriodService;

    @Autowired
    private AuthenticationAccessService authenticationAccessService;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private CollectionConversionService collectionConversionService;

    @Override
    @PerformanceMonitor
    @Secured({ROLE_USER, ROLE_ANONYMOUS})
    public ResponseEntity<ReportingPeriodDTO> createReportingPeriod(@RequestBody ReportingPeriodDTO periodDTO) {

        String surveyName = periodDTO.getSurvey() != null ? periodDTO.getSurvey().getSurveyName() : "undefined";
        log.info("Creating new reporting period for survey {}.", surveyName);

        ReportingPeriod reportingPeriod = conversionService.convert(periodDTO, ReportingPeriod.class);
        reportingPeriod = reportingPeriodService.saveReportingPeriod(reportingPeriod);
        ReportingPeriodDTO result = conversionService.convert(reportingPeriod, ReportingPeriodDTO.class);

        return ResponseEntity.ok(result);
    }

    @Override
    @Secured({ROLE_USER, ROLE_ANONYMOUS})
    public ResponseEntity<ReportingPeriodDTO> updateReportingPeriod(@PathVariable("identifier") Long reportingPeriodGid,
        @RequestBody ReportingPeriodDTO periodDTO) {

        ReportingPeriod reportingPeriod = conversionService.convert(periodDTO, ReportingPeriod.class);
        reportingPeriod = reportingPeriodService.saveReportingPeriod(reportingPeriod);
        ReportingPeriodDTO result = conversionService.convert(reportingPeriod, ReportingPeriodDTO.class);

        return ResponseEntity.ok(result);
    }

    @Override
    @PerformanceMonitor
    public ResponseEntity<ReportingPeriodOverviewListDTO> getReportingPeriods(@RequestParam(name = "unitId", required = false) final String unitId,
        @RequestParam(name = "surveyGid", required = false) final Long surveyGid) {

        List<ReportingPeriodOverview> overviews = null;
        if (surveyGid != null) {
            overviews = reportingPeriodService.loadReportingPeriodOverviewsForSurvey(surveyGid);
        } else {
            overviews = reportingPeriodService.loadReportingPeriodOverviews(unitId);
        }

        final List<ReportingPeriodOverviewDTO> overviewDTOs = collectionConversionService.convert(overviews, ReportingPeriodOverviewDTO.class);

        log.info("Found {} running surveys in unit {} for user {}", overviewDTOs.size(), unitId, authenticationAccessService.getCurrentUsername());

        return new ResponseEntity<>(ReportingPeriodOverviewListDTO.builder().overviews(overviewDTOs).build(), OK);
    }

    @Override
    @Secured({ROLE_USER, ROLE_ANONYMOUS})
    public ResponseEntity<ReportingPeriodDTO> getReportingPeriod(@PathVariable("identifier") Long reportingPeriodGid) {

        ReportingPeriod reportingPeriod = reportingPeriodService.loadReportingPeriod(reportingPeriodGid, 0);

        ReportingPeriodDTO dto = conversionService.convert(reportingPeriod, ReportingPeriodDTO.class);

        return ResponseEntity.ok(dto);
    }
    
    @Override
    @Secured({ROLE_USER, ROLE_ANONYMOUS})
    public ResponseEntity<List<ReportingPeriodDTO>> getReportingPeriods(@PathVariable("foreignSurveyId") String foreignSurveyId) {

        List<ReportingPeriod> reportingPeriods = reportingPeriodService.loadReportingPeriods(foreignSurveyId);

        List<ReportingPeriodDTO> dto = collectionConversionService.convert(reportingPeriods, ReportingPeriodDTO.class);

        return ResponseEntity.ok(dto);
    }

    @Override
    @Secured({ROLE_USER, ROLE_ANONYMOUS})
    public ResponseEntity<ReportingPeriodDataDTO> getReportingPeriodData(@PathVariable("identifier") Long reportingPeriodId) {

        ReportingPeriodDetailQueryResult reportingPeriodData = reportingPeriodService.loadReportingPeriodData(reportingPeriodId);
        ReportingPeriodDataDTO reportingPeriodDataDTO = conversionService.convert(reportingPeriodData, ReportingPeriodDataDTO.class);

        return ResponseEntity.ok(reportingPeriodDataDTO);
    }

    @Override
    public ResponseEntity<Void> deleteReportingPeriod(@PathVariable("identifier") Long reportingPeriodId) {

        ReportingPeriod reportingPeriod = reportingPeriodService.loadReportingPeriod(reportingPeriodId);
        reportingPeriod.setStatus(ReportingPeriodStatus.OBSOLETE);

        reportingPeriodService.saveReportingPeriod(reportingPeriod);

        return ResponseEntity.status(204).build();
    }
    
    @Override
    public ResponseEntity<Void> updateReportingPeriod(@PathVariable("identifier") Long reportingPeriodId, @PathVariable("status") String status) {
        
        log.info("received request to update the status of reportingPeriod {}. New status is {}", reportingPeriodId, status);
        ReportingPeriodStatus newStatus = ReportingPeriodStatus.valueOf(status);
        if(newStatus == null) {
            return ResponseEntity.badRequest().build();
        }
        ReportingPeriod reportingPeriod = reportingPeriodService.loadReportingPeriod(reportingPeriodId);
        reportingPeriod.setStatus(newStatus);

        reportingPeriodService.saveReportingPeriod(reportingPeriod);

        return ResponseEntity.ok().build();
    }

}
