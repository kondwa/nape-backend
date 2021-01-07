/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.resource;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mainlevel.monitoring.admin.api.dto.template.SurveyTemplateDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.template.CustomTemplateDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.template.CustomTemplateListDTO;
import com.mainlevel.monitoring.common.resource.dto.ErrorDTO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Resource for loading survey templates.
 */
@RequestMapping(value = CustomTemplateResourceV2.URI, produces = APPLICATION_JSON_VALUE)
public interface CustomTemplateResourceV2 {

    /** Resource Mapping URI */
    final String URI = "/templates/v2";

    /**
     * Find CUSTOM templates for the given filter criteria.
     *
     * @return list of CUSTOM templates
     */
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Collection of templates that match with the given filter.", response = CustomTemplateListDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorDTO.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ErrorDTO.class)})
    @ApiOperation(value = "Find all survey templates.", notes = "Find survey templates for the given filter criteria.")
    @RequestMapping(value = "/", method = GET)
    ResponseEntity<CustomTemplateListDTO> loadTemplates();

    /**
     * Return composite survey(with survey template) with the given identifier and version.
     *
     * @param identifier survey identifier
     * @param version the template version
     * @return survey with current version of template.
     */
    @ApiResponses(value = {@ApiResponse(code = 200, message = "The exact version of survey.", response = SurveyTemplateDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorDTO.class),
        @ApiResponse(code = 404, message = "Target survey with given identifier and version doesn't exists.", response = ErrorDTO.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ErrorDTO.class)})
    @ApiOperation(value = "Get the exact version of survey.", notes = "Get the exact version of survey.")
    @RequestMapping(value = "/{identifier}/version/{version}", method = GET)
    ResponseEntity<CustomTemplateDTO> getSurveyByVersion(@PathVariable("identifier") String identifier, @PathVariable("version") Long version);

}
