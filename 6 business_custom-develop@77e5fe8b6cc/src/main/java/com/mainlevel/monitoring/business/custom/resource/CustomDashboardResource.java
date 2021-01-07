/*
 * Copyright (c) 2018 Mainlevel Consulting AG. All rights reserved.
 */
package com.mainlevel.monitoring.business.custom.resource;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mainlevel.monitoring.business.custom.resource.dto.dashboard.CustomDashboardDTO;
import com.mainlevel.monitoring.common.resource.dto.ErrorDTO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Resource for loading indicator groups and indicators.
 */
@RequestMapping(value = CustomDashboardResource.URI, produces = APPLICATION_JSON_VALUE)
public interface CustomDashboardResource {

    /** Resource Mapping URI */
    final String URI = "/dashboards";

    /**
     * Load the dashboard for the given identifier.
     *
     * @param key dashboard identifier
     * @return the dashboard
     */
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return a single dashboard configuration.", response = CustomDashboardDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized access", response = ErrorDTO.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ErrorDTO.class)})
    @ApiOperation(value = "Load the dashboard for the given identifier.")
    @RequestMapping(value = "/{key}", method = GET, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<CustomDashboardDTO> getDashboard(@PathVariable("key") String key);

    /**
     * Update the item configuration of the dashboard.
     *
     * @param key dashboard key
     * @param itemKey dashboard item key
     * @param properties dashboard item properties to update
     * @return updated item
     */
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return the updated properties."),
        @ApiResponse(code = 401, message = "Unauthorized access", response = ErrorDTO.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ErrorDTO.class)})
    @ApiOperation(value = "Update the item configuration of the dashboard.")
    @RequestMapping(value = "/{key}/items/{itemKey}/properties", method = PUT, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<Map<String, Object>> updateDashboardItemProperties(@PathVariable("key") String key, @PathVariable("itemKey") String itemKey,
        @RequestBody Map<String, Object> properties);
}
