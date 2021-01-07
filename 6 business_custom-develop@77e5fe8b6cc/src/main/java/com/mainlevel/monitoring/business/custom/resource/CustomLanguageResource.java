/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */
package com.mainlevel.monitoring.business.custom.resource;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mainlevel.monitoring.business.custom.resource.dto.CustomLanguageListDTO;
import com.mainlevel.monitoring.common.resource.dto.ErrorDTO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Resource for loading available languages.
 */
@RequestMapping(value = CustomLanguageResource.URI, produces = APPLICATION_JSON_VALUE)
public interface CustomLanguageResource {

    /** Resource Mapping URI */
    final String URI = "/languages";

    /**
     * Load all existing languages.
     *
     * @return the list of languages
     */
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Collection of all languages.", response = CustomLanguageListDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorDTO.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ErrorDTO.class)})
    @ApiOperation(value = "Get all languages", notes = "Get all languages")
    @RequestMapping(value = "/", method = GET)
    ResponseEntity<CustomLanguageListDTO> findAll();

}
