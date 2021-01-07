/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.resource;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mainlevel.monitoring.business.custom.resource.dto.user.CustomUserDTO;

/**
 * Resource for loading user roles.
 */
@RequestMapping(value = CustomUserResource.URI, produces = APPLICATION_JSON_VALUE)
public interface CustomUserResource {

    /** Resource Mapping URI */
    final String URI = "/users";

    /**
     * Load paginaged roles.
     *
     * @return the paged resource
     */
    @RequestMapping(value = "/current", method = GET)
    public ResponseEntity<CustomUserDTO> getCurrentUser();

}
