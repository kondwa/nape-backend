/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.admin.api.resource;

import static com.mainlevel.monitoring.admin.api.Admin.SERVICE_NAME;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mainlevel.monitoring.admin.api.dto.indicator.IndicatorGroupListDTO;
import com.mainlevel.monitoring.admin.api.dto.indicator.IndicatorListDTO;

/**
 * Resource for maintaining indicators.
 */
@FeignClient(SERVICE_NAME)
public interface IndicatorResource {

    /** Resource Mapping URI */
    final String URI = "/indicatorGroups";

    /**
     * Load all available indicator groups.
     *
     * @return the list of groups
     */
    @RequestMapping(value = URI, method = GET, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<IndicatorGroupListDTO> getIndicatorGroups();

    /**
     * Load the indicator group with the given id.
     *
     * @param groupId id of the group
     * @return the list of indicators
     */
    @RequestMapping(value = URI + "/{identifier}/indicators", method = GET, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<IndicatorListDTO> getIndicators(@PathVariable("identifier") String groupId);

}
