/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.resource;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mainlevel.monitoring.business.custom.resource.dto.survey.CustomSurveyInstanceDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.survey.CustomSurveyInstanceListDTO;
import com.mainlevel.monitoring.common.resource.dto.ErrorDTO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Resource for maintaining survey instances.
 */
@RequestMapping(value = CustomSurveyInstanceResource.URI, produces = APPLICATION_JSON_VALUE)
public interface CustomSurveyInstanceResource {

    /** Resource Mapping URI */
    final String URI = "/surveys/{surveyGid}/instances";

    /**
     * Load a list of survey instances for the given filter parameters.
     *
     * @param unitId the org unit id to filter
     * @param surveyGid the survey graph id
     * @return the list of loaded surveys
     */
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return the list of survey instances.", response = CustomSurveyInstanceListDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized access", response = ErrorDTO.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ErrorDTO.class)})
    @ApiOperation(value = "Find all instances for the given unit or survey.")
    @RequestMapping(value = "/", method = GET, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<CustomSurveyInstanceListDTO> getSurveyInstances(@RequestParam(name = "unitId", required = false) String unitId,
        @PathVariable(name = "surveyGid", required = true) final Long surveyGid);

    /**
     * Returns the amount of instances of the given survey.
     *
     * @param unitId the org unit id to filter
     * @param surveyGid the survey graph id
     * @return the list of loaded surveys
     */
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return number of survey instances.", response = CustomSurveyInstanceListDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized access", response = ErrorDTO.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ErrorDTO.class)})
    @ApiOperation(value = "Count" + "all instances for the given unit or survey.")
    @RequestMapping(value = "/size", method = GET, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<Integer> getSurveyInstanceCount(@RequestParam(name = "unitId", required = false) String unitId,
        @PathVariable(name = "surveyGid", required = true) final Long surveyGid);

    /**
     * Load a single survey instance.
     *
     * @param surveyGid the survey graph id
     * @param surveyInstanceGid the survey instance graph id
     * @return the survey instance
     */
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return the survey instance.", response = CustomSurveyInstanceDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized access", response = ErrorDTO.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ErrorDTO.class)})
    @ApiOperation(value = "Find a survey instance for the given ids.")
    @RequestMapping(value = "/{surveyInstanceGid}", method = GET, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<CustomSurveyInstanceDTO> getSurveyInstance(@PathVariable(name = "surveyGid", required = true) final Long surveyGid,
        @PathVariable(name = "surveyInstanceGid", required = true) final Long surveyInstanceGid);

    /**
     * Creates a new survey instance for the given paramter.
     *
     * @param surveyGid the survey graph id
     * @param body the survey instance body
     * @param ipAddress the client ip address
     * @return the created survey instance
     */
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return the created survey instance.", response = CustomSurveyInstanceDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized access", response = ErrorDTO.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ErrorDTO.class)})
    @ApiOperation(value = "Creates a new survey instance for the given paramter.")
    @RequestMapping(value = "/", method = POST, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<CustomSurveyInstanceDTO> createSurveyInstance(@PathVariable(name = "surveyGid", required = true) final Long surveyGid,
        @RequestBody CustomSurveyInstanceDTO body, @RequestHeader("x-forwarded-for") String ipAddress);

    /**
     * Updates an existing survey instance for the given paramter.
     *
     * @param surveyGid the survey graph id
     * @param surveyInstanceGid the survey instance graph id
     * @param body the survey instance body
     * @param ipAddress the client ip address
     * @return the updated survey instance
     */
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return the saved survey instance.", response = CustomSurveyInstanceDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized access", response = ErrorDTO.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ErrorDTO.class)})
    @ApiOperation(value = "Saves an existing survey instance for the given paramter.")
    @RequestMapping(value = "/{surveyInstanceGid}", method = PUT, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<CustomSurveyInstanceDTO> updateSurveyInstance(@PathVariable(name = "surveyGid", required = true) final Long surveyGid,
        @PathVariable(name = "surveyInstanceGid", required = true) final Long surveyInstanceGid, @RequestBody CustomSurveyInstanceDTO body,
        @RequestHeader("x-forwarded-for") String ipAddress);

    /**
     * Changes the status of the given survey instance.
     *
     * @param surveyGid the survey graph id
     * @param surveyInstanceGid the survey instance graph id
     * @param surveyInstanceStatus the new status to be set
     * 
     * @return empty response
     */
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Survey Instance Status updated successfully."),
        @ApiResponse(code = 401, message = "Unauthorized access", response = ErrorDTO.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ErrorDTO.class)})
    @ApiOperation(value = "Changes the status of the given survey instance.")
    @RequestMapping(value = "/{surveyInstanceGid}/{surveyInstanceStatus}", method = PUT, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<Void> updateSurveyInstanceStatus(@PathVariable(name = "surveyGid", required = true) final Long surveyGid,
        @PathVariable(name = "surveyInstanceGid", required = true) final Long surveyInstanceGid, @PathVariable(name = "surveyInstanceStatus", required = true) final String surveyInstanceStatus);
    
    /**
     * Delete a survey instance.
     *
     * @param surveyGid the survey graph id
     * @param surveyInstanceGid the survey instance graph id
     * @return empty response
     */
    @ApiResponses(value = {@ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 401, message = "Unauthorized access", response = ErrorDTO.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ErrorDTO.class)})
    @ApiOperation(value = "Delete the survey instance for the given ids.")
    @RequestMapping(value = "/{surveyInstanceGid}", method = DELETE, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteSurveyInstance(@PathVariable(name = "surveyGid", required = true) final Long surveyGid,
        @PathVariable(name = "surveyInstanceGid", required = true) final Long surveyInstanceGid);

}
