/*
 * Copyright (c) 2018 Mainlevel Consulting AG. All rights reserved.
 */
package com.mainlevel.monitoring.admin.api.resource;

import static com.mainlevel.monitoring.admin.api.Admin.SERVICE_NAME;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mainlevel.monitoring.admin.api.dto.user.UserDTO;

/**
 * Resource for creating users.
 */
@FeignClient(SERVICE_NAME)
public interface UserResource {

    /** Resource Mapping URI */
    public static final String URI = "/users";

    /**
     * Load all information for the current user.
     *
     * @return the user
     */
    @RequestMapping(value = URI + "/current", method = GET, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<UserDTO> getCurrentUser();

    /**
     * Creates a new user for the given body.
     *
     * @param user the user to create
     * @return the created user
     */
    @RequestMapping(value = URI, method = POST, produces = APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<UserDTO> createUser(@RequestBody UserDTO user);

}
