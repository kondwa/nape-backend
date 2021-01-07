/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.resource;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mainlevel.monitoring.business.custom.resource.dto.CustomTokenDTO;

/**
 * Interface for obtaining the login.
 */
@RequestMapping(value = CustomTokenResource.URI, produces = APPLICATION_JSON_VALUE)
public interface CustomTokenResource {

    /** Resource Mapping URI */
    String URI = "/token";

    /**
     * Performs the login based on basic auth credentials provided in HTTP Headers.
     *
     * @return the token response
     */
    @RequestMapping(method = GET)
    ResponseEntity<CustomTokenDTO> doLogin();

}
