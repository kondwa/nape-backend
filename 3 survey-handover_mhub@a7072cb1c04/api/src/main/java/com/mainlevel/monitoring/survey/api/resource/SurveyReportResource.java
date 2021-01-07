/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.api.resource;

import static com.mainlevel.monitoring.survey.api.Survey.SERVICE_NAME;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mainlevel.monitoring.survey.api.dto.SurveySummaryResultListDTO;

/**
 * REST resource for loading reports of a specific survey.
 */
@FeignClient(SERVICE_NAME)
public interface SurveyReportResource {

    /** Resource Mapping URI */
    final String URI = "/surveys/{identifier}/report";

    /**
     * Load the survey for the given name and return it as CSV.
     *
     * @param surveyGid the survey graph id
     * @param reportName name of the report
     * @param parameters list of report parameters
     * @return the report result list
     */
    @RequestMapping(value = URI + "/{reportName}", method = GET, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<SurveySummaryResultListDTO> loadSurveyReportAsCSV(@PathVariable("identifier") final Long surveyGid,
        @PathVariable("reportName") final String reportName, @RequestParam final Map<String, String> parameters);

}
