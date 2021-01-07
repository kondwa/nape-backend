/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.resource;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mainlevel.monitoring.business.custom.resource.dto.indicator.CustomIndicatorDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.indicator.CustomIndicatorGroupListDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.indicator.CustomIndicatorListDTO;

/**
 * Resource for loading indicator groups and indicators.
 */
@RequestMapping(value = CustomIndicatorResource.URI, produces = APPLICATION_JSON_VALUE)
public interface CustomIndicatorResource {

    /** Resource Mapping URI */
    final String URI = "/indicatorGroups";

    /**
     * Load all available indicator groups.
     *
     * @return the list of groups
     */
    @RequestMapping(method = GET)
    ResponseEntity<CustomIndicatorGroupListDTO> getIndicatorGroups();

    /**
     * Load the indicator group with the given id.
     *
     * @param groupId id of the group
     * @param filterParams the filter parameters
     * @return the list of indicators
     */
    @RequestMapping(value = "/{identifier}/indicators", method = GET)
    ResponseEntity<CustomIndicatorListDTO> getIndicators(@PathVariable("identifier") String groupId, @RequestParam Map<String, String> filterParams);

    /**
     * Load the indicator group with the given id.
     *
     * @param groupId id of the group
     * @param indicatorKey key of the indicator
     * @param filterParams filter parameters
     * @return the list of indicators
     */
    @RequestMapping(value = "/{identifier}/indicators/{key}", method = GET)
    ResponseEntity<CustomIndicatorDTO> getIndicator(@PathVariable("identifier") String groupId, @PathVariable("key") String indicatorKey,
        @RequestParam Map<String, String> filterParams);

}
