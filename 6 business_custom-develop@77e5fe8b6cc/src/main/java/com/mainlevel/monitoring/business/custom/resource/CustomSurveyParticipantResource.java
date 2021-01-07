/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.resource;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mainlevel.monitoring.business.custom.resource.dto.user.CustomSureyParticipantDTO;

/**
 * REST resource for maintaining participations of users on surveys.
 */
@RequestMapping(value = CustomSurveyParticipantResource.URI, produces = APPLICATION_JSON_VALUE)
public interface CustomSurveyParticipantResource {

    /** Resource Mapping URI */
    String URI = "/surveys/{surveyGid}/participants";

    /**
     * Retrieve the root unit.
     *
     * @param surveyGid graph id of the survey
     * @param user the user to create
     * @param protocol the protocol
     * @param host the host name
     * @param ipAddress the ip address
     * @return the unit
     */
    @RequestMapping(method = POST)
    ResponseEntity<CustomSureyParticipantDTO> createParticipantForSurvey(@PathVariable("surveyGid") Long surveyGid,
        @RequestBody CustomSureyParticipantDTO user, @RequestHeader("x-forwarded-proto") String protocol,
        @RequestHeader("x-forwarded-host") String host, @RequestHeader("x-forwarded-for") String ipAddress);

}
