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

import com.mainlevel.monitoring.survey.api.dto.survey.ParticipantSessionDTO;

/**
 * Resource for loading and saving participant sessions.
 */
@FeignClient(SERVICE_NAME)
public interface ParticipantSessionResource {

    /** Resource Mapping URI */
    final String URI = "/participantSessions";

    /**
     * Creates a new participant session.
     *
     * @param survey the participant session to create
     * @return the saved participant session
     */
    @RequestMapping(value = URI, method = POST, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<ParticipantSessionDTO> saveSession(@RequestBody ParticipantSessionDTO survey);

    /**
     * Load the latest participant session for a reporting period id.
     * 
     * @param reportingPeriodGid graph id of reporting period
     * @return the latest participant session
     */
    @RequestMapping(value = URI + "/{reportingPeriodGid}", method = GET, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<ParticipantSessionDTO> getLatestSession(@PathVariable(name = "reportingPeriodGid", required = true) Long reportingPeriodGid);

}
