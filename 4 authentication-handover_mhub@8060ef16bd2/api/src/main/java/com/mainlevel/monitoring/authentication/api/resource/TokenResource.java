/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.authentication.api.resource;

import static com.mainlevel.monitoring.authentication.api.Authentication.SERVICE_NAME;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mainlevel.monitoring.authentication.api.dto.TokenDTO;

/**
 * Endpoint for receiving the JWT token.
 */
@FeignClient(SERVICE_NAME)
public interface TokenResource {

    /** Resource Mapping URI */
    String URI = "/token";

    /**
     * Performs the login based on basic auth credentials provided in HTTP Headers.
     *
     * @return the token response
     */
    @RequestMapping(value = URI, method = GET)
    ResponseEntity<TokenDTO> doLogin();

    /**
     * Check expiration date for JWT.
     *
     * @return nothing
     */
    @RequestMapping(value = URI + "/verify", method = GET, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<Void> checkIfTokenIsValid();

}
