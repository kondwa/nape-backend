/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.resource;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mainlevel.monitoring.business.custom.resource.dto.survey.CustomSurveyActivationDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.survey.CustomSurveyDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.survey.CustomSurveyInstanceListDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.survey.CustomSurveyStatsListDTO;
import com.mainlevel.monitoring.common.resource.dto.ErrorDTO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Resource for loading and maintaining surveys.
 */
@RequestMapping(value = CustomSurveyResourceV2.URI, produces = APPLICATION_JSON_VALUE)
public interface CustomSurveyResourceV2 {

    /** Resource Mapping URI */
    final String URI = "/v2/surveys";

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
    @RequestMapping(value = "/", method = GET, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<CustomSurveyStatsListDTO> getSurveyStats(@RequestParam(name = "unitKey", required = false) String unitKey);

    /**
     * Create a new survey for the given parameters.
     *
     * @param body the activation body
     * @return the created survey
     */
    @ApiResponses(value = {@ApiResponse(code = 200, message = "The created survey object.", response = CustomSurveyDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized access", response = ErrorDTO.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ErrorDTO.class)})
    @ApiOperation(value = "Activate a new survey for the given parameter.")
    @RequestMapping(value = "/", method = POST, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<CustomSurveyDTO> activateSurvey(@RequestBody CustomSurveyActivationDTO body);

    /**
     * Load the survey for the given graph id.
     *
     * @param surveyGid the survey graph id
     * @param protocol forwarded protocol
     * @param host forwarded host
     * @return the loaded survey
     */
    @ApiResponses(value = {@ApiResponse(code = 200, message = "The loaded survey object.", response = CustomSurveyDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized access", response = ErrorDTO.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ErrorDTO.class)})
    @ApiOperation(value = "Load the survey for the given id.")
    @RequestMapping(value = "/{identifier}", method = GET, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<CustomSurveyDTO> getSurvey(@PathVariable("identifier") Long surveyGid, @RequestHeader("x-forwarded-proto") String protocol,
        @RequestHeader("x-forwarded-host") String host);

}
