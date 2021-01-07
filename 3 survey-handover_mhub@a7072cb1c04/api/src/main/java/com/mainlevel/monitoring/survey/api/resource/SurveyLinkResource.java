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

import com.mainlevel.monitoring.survey.api.dto.SurveyLinkDTO;

/**
 * REST resource for creating and loading weblinks for surveys.
 */
@FeignClient(SERVICE_NAME)
public interface SurveyLinkResource {

    /** Resource Mapping URI */
    final String URI = "/links";

    /**
     * Load the link for the given token.
     *
     * @param token the link token
     * @return the stored link
     */
    @RequestMapping(value = URI + "/{linkToken}", method = GET, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<SurveyLinkDTO> getLink(@PathVariable(name = "linkToken", required = true) String token);

    /**
     * Creates the given link in the databse.
     *
     * @param link the link to store
     * @return the stored link
     */
    @RequestMapping(value = URI, method = POST, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<SurveyLinkDTO> createLink(@RequestBody SurveyLinkDTO link);

}
