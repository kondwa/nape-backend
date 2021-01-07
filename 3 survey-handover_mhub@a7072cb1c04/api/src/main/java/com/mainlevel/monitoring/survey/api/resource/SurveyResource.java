/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.api.resource;

import static com.mainlevel.monitoring.survey.api.Survey.SERVICE_NAME;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mainlevel.monitoring.survey.api.dto.SurveyLinkDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.SurveyDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.SurveyListDTO;

/**
 * Resource for loading and saving surveys.
 */
@FeignClient(SERVICE_NAME)
public interface SurveyResource {

    /** Resource Mapping URI */
    final String URI = "/surveys";

    /**
     * Creates a new survey.
     *
     * @param survey the survey to create
     * @return the saved survey
     */
    @RequestMapping(value = URI, method = POST, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<SurveyDTO> createSurvey(@RequestBody SurveyDTO survey);

    /**
     * Load the survey with the given graph id.
     *
     * @param surveyGid the survey graph id
     * @param includingStructure flag indicating to whether to load the survey structure (question groups and questions) or not
     * @return the loaded survey
     */
    @RequestMapping(value = URI + "/{identifier}", method = GET, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<SurveyDTO> getSurvey(@PathVariable(name = "identifier", required = true) Long surveyGid,
        @RequestParam(name = "includingStructure", required = false) Boolean includingStructure);

    /**
     * Loads the survey for the given graph id.
     *
     * @param templateId template reference id
     * @param version template version
     * @return the survey graph id
     */
    @RequestMapping(value = URI + "/", method = GET, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<SurveyListDTO> getSurveysForTemplate(@RequestParam("templateId") String templateId, @RequestParam("templateVersion") Long version);

    /**
     * Loads the link for an anonymous survey graph id.
     *
     * @param graphId the survey graph id
     * @return the link
     */
    @RequestMapping(value = URI + "/{identifier}/link", method = GET, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<SurveyLinkDTO> getSurveyLink(@RequestParam(name = "identifier", required = true) Long graphId);
}
