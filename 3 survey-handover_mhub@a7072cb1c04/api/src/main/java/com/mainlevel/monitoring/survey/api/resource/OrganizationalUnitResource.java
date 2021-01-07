/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.api.resource;

import static com.mainlevel.monitoring.survey.api.Survey.SERVICE_NAME;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mainlevel.monitoring.survey.api.dto.OrganizationalUnitDTO;
import com.mainlevel.monitoring.survey.api.dto.user.SurveyUserListDTO;

/**
 * REST resource for organizational units.
 */
@FeignClient(SERVICE_NAME)
public interface OrganizationalUnitResource {

    /** Resource Mapping URI */
    final String URI = "/units";

    /**
     * Load the organizational unit with the given name.
     *
     * @param name name of the organizational unit
     * @param foreignId foreign id of the organizational unit
     * @return the unit
     */
    @RequestMapping(value = URI, method = GET)
    ResponseEntity<OrganizationalUnitDTO> getUnit(@RequestParam(name = "name", required = false) String name,
        @RequestParam(name = "foreignId", required = false) String foreignId);

    /**
     * Creates a new organizational unit for the given parameter.
     *
     * @param body the unit to create
     * @return the list of users
     */
    @RequestMapping(value = URI, method = POST)
    ResponseEntity<OrganizationalUnitDTO> createUnit(@RequestBody OrganizationalUnitDTO body);

    /**
     * Updates an organizational unit for the given parameter.
     *
     * @param foreignId org unit foreign id
     * @param body the unit to create
     * @return the list of users
     */
    @RequestMapping(value = URI + "/{foreignId}", method = PUT)
    ResponseEntity<OrganizationalUnitDTO> updateUnit(@PathVariable(name = "foreignId") String foreignId, @RequestBody OrganizationalUnitDTO body);

    /**
     * Delete the unit with the given foreign id.
     *
     * @param foreignId unit foreign id to remove
     * @return the deleted unit
     */
    @RequestMapping(value = URI + "/{foreignId}", method = DELETE)
    ResponseEntity<OrganizationalUnitDTO> deleteUnit(@PathVariable(name = "foreignId") String foreignId);

    /**
     * Loads the users for the organizational unit with the given foreign id.
     *
     * @param foreignId foreign id of the unit
     * @return the list of users participating on this unit
     */
    @RequestMapping(value = URI + "/{foreignId}/users", method = GET)
    ResponseEntity<SurveyUserListDTO> getUsersForUnit(@PathVariable(name = "foreignId") String foreignId);

    /**
     * Retrieve the participants for the unit with the given key.
     *
     * @param foreignId unique key of this unit
     * @param body the survey user body
     * @return the list of users
     */
    @RequestMapping(value = URI + "/{foreignId}/users", method = POST)
    ResponseEntity<SurveyUserListDTO> setUsersForUnit(@RequestBody SurveyUserListDTO body, @PathVariable(name = "foreignId") String foreignId);

    /**
     * Removes the participation for the given user.
     *
     * @param foreignId unique key of this unit
     * @param graphId graph id of the user
     * @return nothing
     */
    @RequestMapping(value = URI + "/{foreignId}/users/{gid}", method = DELETE)
    ResponseEntity<Void> deleteUserParticipation(@PathVariable(name = "foreignId") String foreignId, @PathVariable("gid") Long graphId);

}
