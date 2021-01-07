/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */
package com.mainlevel.monitoring.survey.resource;

import static com.mainlevel.monitoring.common.constant.RoleConstant.ROLE_USER;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mainlevel.monitoring.common.service.CollectionConversionService;
import com.mainlevel.monitoring.survey.api.dto.SurveySummaryResultDTO;
import com.mainlevel.monitoring.survey.api.dto.SurveySummaryResultListDTO;
import com.mainlevel.monitoring.survey.api.resource.SurveyReportResource;
import com.mainlevel.monitoring.survey.database.queryresult.SurveySummaryResult;
import com.mainlevel.monitoring.survey.service.SurveyReportService;

/**
 * Default implementation of {@link SurveyReportResource}.
 */
@RestController
@Secured(ROLE_USER)
public class SurveyReportResourceImpl implements SurveyReportResource {

    @Autowired
    private SurveyReportService reportService;

    @Autowired
    private CollectionConversionService collectionConversionService;

    @Override
    public ResponseEntity<SurveySummaryResultListDTO> loadSurveyReportAsCSV(@PathVariable("identifier") final Long surveyGid,
        @PathVariable("reportName") final String reportName, @RequestParam final Map<String, String> parameters) {

        List<SurveySummaryResult> results = reportService.loadSurveySummary(surveyGid);

        List<SurveySummaryResultDTO> resultDTOs = collectionConversionService.convert(results, SurveySummaryResultDTO.class);
        SurveySummaryResultListDTO dto = SurveySummaryResultListDTO.builder().resultList(resultDTOs).build();

        return ResponseEntity.ok(dto);
    }

}
