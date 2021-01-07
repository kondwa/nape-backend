/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.resource;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mainlevel.monitoring.business.custom.resource.dto.survey.CustomSurveyInstanceDTO;
import com.mainlevel.monitoring.common.resource.dto.ErrorDTO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * External REST resource for loading and saving a survey via link.
 */
@RequestMapping(value = CustomExternalSurveyLinkResource.URI, produces = APPLICATION_JSON_VALUE)
public interface CustomExternalSurveyLinkResource {

    /** Resource Mapping URI */
    final String URI = "/external/surveys/{linkToken}";

    /**
     * Load a single survey instance.
     *
     * @param linkToken token to the survey link
     * @param ipAddress the client ip address
     * @return the survey instance
     */
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return the survey instance.", response = CustomSurveyInstanceDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized access", response = ErrorDTO.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ErrorDTO.class)})
    @ApiOperation(value = "Find a survey instance for the given link token.")
    @RequestMapping(method = POST, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<CustomSurveyInstanceDTO> getSurveyInstance(@PathVariable(name = "linkToken", required = true) String linkToken,
        @RequestHeader("x-forwarded-for") String ipAddress);

    /**
     * Updates an existing survey instance for the given paramter.
     *
     * @param linkToken token to the survey link
     * @param body the survey instance body
     * @param ipAddress the client ip address
     * @return the updated survey instance
     */
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return the saved survey instance.", response = CustomSurveyInstanceDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized access", response = ErrorDTO.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ErrorDTO.class)})
    @ApiOperation(value = "Saves an existing survey instance for the given paramter.")
    @RequestMapping(method = PUT, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<CustomSurveyInstanceDTO> updateSurveyInstance(@PathVariable(name = "linkToken", required = true) String linkToken,
        @RequestBody CustomSurveyInstanceDTO body, @RequestHeader("x-forwarded-for") String ipAddress);
}
