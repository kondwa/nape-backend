/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.api.resource;

import static com.mainlevel.monitoring.survey.api.Survey.SERVICE_NAME;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mainlevel.monitoring.survey.api.dto.survey.SurveyStatsListDTO;

/**
 * Resource for loading survey statistics.
 */
@FeignClient(SERVICE_NAME)
public interface SurveyStatsResource {

    /** Resource Mapping URI */
    final String URI = "/surveys/stats";

    /**
     * Loads all existing surveys.
     * 
     * @param projectKey the project foreign id
     * @return the list of surveys
     */
    @RequestMapping(value = URI, method = GET, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<SurveyStatsListDTO> getSurveyStats(@RequestParam(name = "projectKey", required = false) String projectKey);
}
