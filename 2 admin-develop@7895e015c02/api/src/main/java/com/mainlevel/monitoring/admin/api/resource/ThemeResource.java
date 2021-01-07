/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.admin.api.resource;

import static com.mainlevel.monitoring.admin.api.Admin.SERVICE_NAME;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mainlevel.monitoring.admin.api.dto.theme.ThemeDTO;

/**
 * Resource for loading and maintaining application themes.
 */
@FeignClient(SERVICE_NAME)
public interface ThemeResource {

    /** Resource Mapping URI */
    public static final String URI = "/themes";

    /**
     * Load the theme with the given name.
     *
     * @param name name of the theme
     * @return theme with the given name
     */
    @RequestMapping(value = URI + "/{name}", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ThemeDTO> getTheme(@PathVariable("name") String name);
}
