/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.admin.api.resource;

import static com.mainlevel.monitoring.admin.api.Admin.SERVICE_NAME;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mainlevel.monitoring.admin.api.dto.unit.UnitDTO;
import com.mainlevel.monitoring.admin.api.dto.unit.UnitListDTO;
import com.mainlevel.monitoring.admin.api.dto.user.UserListDTO;

/**
 * Resource for loading/maintaining organizational units.
 */
@FeignClient(SERVICE_NAME)
public interface UnitResource {

    /** Resource Mapping URI */
    final String URI = "/units";

    /**
     * Load the units for the given type.
     *
     * @param type type of units
     * @return list of units
     */
    @RequestMapping(value = URI, method = RequestMethod.GET, params = {"type"}, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<UnitListDTO> getUnits(@RequestParam("type") String type);

    /**
     * Get the organizational unit with the given filter criteria.
     *
     * @param id the unit database id
     * @return the unit
     */
    @RequestMapping(value = URI, method = RequestMethod.GET, params = {"id"}, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<UnitDTO> getUnitById(@RequestParam("id") String id);

    /**
     * Get the root organizational unit, with deep loaded sub-entities.
     *
     * @return the root unit
     */
    @RequestMapping(value = URI + "/root", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<UnitDTO> getRoot();

    /**
     * Get the organizational unit with the given key, with deep loaded sub-entities.
     *
     * @param foreignId the unit foreign id
     * @return the unit
     */
    @RequestMapping(value = URI + "/{foreignId}", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<UnitDTO> getUnit(@PathVariable("foreignId") String foreignId);

    /**
     * Creates a new unit.
     *
     * @param body the body
     * @return the created unit
     */
    @RequestMapping(value = URI, method = POST)
    ResponseEntity<UnitDTO> create(@RequestBody UnitDTO body);

    /**
     * Updates an existing unit.
     *
     * @param foreignId the unit key
     * @param body the body
     * @return the updated unit
     */
    @RequestMapping(value = URI + "/{foreignId}", method = PUT)
    ResponseEntity<UnitDTO> update(@PathVariable("foreignId") String foreignId, @RequestBody UnitDTO body);

    /**
     * Deletes an existing unit.
     *
     * @param foreignId the unit key
     * @return the updated unit
     */
    @RequestMapping(value = URI + "/{foreignId}", method = DELETE)
    ResponseEntity<UnitDTO> delete(@PathVariable("foreignId") String foreignId);

    /**
     * Adds users for the given
     *
     * @param body the list of users
     * @param foreignId the unit id
     * @return the list of users
     */
    @RequestMapping(value = URI + "/{foreignId}/users", method = POST)
    ResponseEntity<UserListDTO> setUsersForUnit(@RequestBody UserListDTO body, @PathVariable(name = "foreignId") String foreignId);

    /**
     * Removes the participation for the given user.
     *
     * @param foreignId unique key of this unit
     * @param username username of the user
     * @return nothing
     */
    @RequestMapping(value = URI + "/{foreignId}/users/{username}", method = DELETE)
    ResponseEntity<Void> deleteUserParticipation(@PathVariable(name = "foreignId") String foreignId, @PathVariable("username") String username);

}
