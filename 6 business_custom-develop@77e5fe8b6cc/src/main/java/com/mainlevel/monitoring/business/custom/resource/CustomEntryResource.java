/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.resource;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mainlevel.monitoring.common.resource.dto.NavigationDTO;

/**
 * Base resource of survey microservice. This resource provides HATEOS navigation within the service.
 */
@RequestMapping(value = CustomEntryResource.URI, produces = APPLICATION_JSON_VALUE)
public interface CustomEntryResource {

    /** Resource Mapping URI */
    final String URI = "/";

    /**
     * Retrieve the links for survey microservice
     *
     * @return the navigation DTO containing the links
     */
    @RequestMapping(method = GET)
    ResponseEntity<NavigationDTO> getNavigation();

}
