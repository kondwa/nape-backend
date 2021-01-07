/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.api.resource;

import static com.mainlevel.monitoring.survey.api.Survey.SERVICE_NAME;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mainlevel.monitoring.survey.api.dto.user.SurveyUserDTO;

/**
 * REST resource for maintaining survey users.
 */
@FeignClient(SERVICE_NAME)
public interface SurveyUserResource {

    /** Resource Mapping URI */
    final String URI = "/users";

    /**
     * Saves a new user.
     *
     * @param user the user to save
     * @return the user
     */
    @RequestMapping(value = URI, method = POST)
    ResponseEntity<SurveyUserDTO> createUser(@RequestBody SurveyUserDTO user);

    /**
     * Loads a user based on its username.
     *
     * @param username the username
     * @return the user with the given username
     */
    @RequestMapping(value = URI, method = GET)
    ResponseEntity<SurveyUserDTO> findUserByUsername(@RequestParam("username") String username);

    /**
     * Update the given user.
     *
     * @param userGid the users graph id
     * @param user the user to save
     * @return the updated user
     */
    @RequestMapping(value = URI + "/{identifier}", method = PUT)
    ResponseEntity<SurveyUserDTO> updateUser(@PathVariable("identifier") Long userGid, @RequestBody SurveyUserDTO user);

}
