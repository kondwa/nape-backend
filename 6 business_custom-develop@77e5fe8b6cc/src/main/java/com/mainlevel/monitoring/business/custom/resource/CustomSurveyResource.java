/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.resource;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mainlevel.monitoring.business.custom.resource.dto.survey.CustomSurveyInstanceDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.survey.CustomSurveyInstanceListDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.survey.CustomSurveyStatsListDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.survey.v1.CustomSurveyDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.survey.v1.CustomSurveySubmitDTO;
import com.mainlevel.monitoring.common.resource.dto.ErrorDTO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Resource for loading and maintaining surveys.
 */
@RequestMapping(value = CustomSurveyResource.URI, produces = APPLICATION_JSON_VALUE)
public interface CustomSurveyResource {

    /** Resource Mapping URI */
    final String URI = "/surveys";

    /**
     * Load a list of survey overviews for the given filter parameters.
     *
     * @param unitKey the org unit id to filter
     * @return the list of loaded surveys
     */
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return the list of survey stats.", response = CustomSurveyInstanceListDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized access", response = ErrorDTO.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ErrorDTO.class)})
    @ApiOperation(value = "Find all survey stats for the given unit.")
    @RequestMapping(value = "/stats/", method = GET, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<CustomSurveyStatsListDTO> getSurveyStats(@RequestParam(name = "unitKey", required = false) String unitKey);

    /**
     * Submit the CUSTOM survey based on the given structure.
     *
     * @param body the CUSTOM survey
     * @param ipAddress ip address of the client
     * @return the saved survey
     */
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return the saved survey.", response = CustomSurveyInstanceDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized access", response = ErrorDTO.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ErrorDTO.class)})
    @ApiOperation(value = "Publish a new survey with the given data.")
    @RequestMapping(value = "/", method = POST, produces = APPLICATION_JSON_VALUE)
    // TODO: Should be refactored to CustomSurveyInstanceResource
    ResponseEntity<CustomSurveyDTO> submitSurveyInstance(@RequestBody CustomSurveySubmitDTO body, @RequestHeader("x-forwarded-for") String ipAddress);

}
