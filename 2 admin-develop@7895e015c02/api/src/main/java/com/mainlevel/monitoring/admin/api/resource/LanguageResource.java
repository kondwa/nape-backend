/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.admin.api.resource;

import static com.mainlevel.monitoring.admin.api.Admin.SERVICE_NAME;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mainlevel.monitoring.admin.api.dto.language.LanguageListDTO;

/**
 * Resource to load and maintain languages.
 */
@FeignClient(SERVICE_NAME)
public interface LanguageResource {

    /** Resource Mapping URI */
    final String URI = "/admin/surveylanguages";

    /**
     * Load all existing languages.
     *
     * @return the list of languages
     */
    @RequestMapping(value = URI, method = GET, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<LanguageListDTO> findAll();

}
