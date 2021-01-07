/*
 * Copyright (c) 2018 Mainlevel Consulting AG. All rights reserved.
 */
package com.mainlevel.monitoring.admin.api.resource;

import static com.mainlevel.monitoring.admin.api.Admin.SERVICE_NAME;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mainlevel.monitoring.admin.api.dto.dashboard.DashboardDTO;

/**
 * Resource for maintaining dashboards.
 */
@FeignClient(SERVICE_NAME)
public interface DashboardResource {

    /** Resource Mapping URI */
    final String URI = "/dashboards";

    /**
     * Load the dashboard with the given id.
     *
     * @param key dashboard key
     * @return the dashboard
     */
    @RequestMapping(value = URI + "/{key}", method = GET, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<DashboardDTO> getDashboard(@PathVariable("key") String key);

    /**
     * Update the item configuration of the dashboard.
     *
     * @param key dashboard key
     * @param itemKey dashboard item key
     * @param properties dashboard item properties to update
     * @return updated item
     */
    @RequestMapping(value = URI + "/{key}/items/{itemKey}/properties", method = PUT, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<Map<String, Object>> updateDashboardItem(@PathVariable("key") String key, @PathVariable("itemKey") String itemKey,
        @RequestBody Map<String, Object> properties);
}
