/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.resource;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mainlevel.monitoring.business.custom.resource.dto.unit.CustomUnitDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.unit.CustomUnitListDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.user.CustomUnitUserListDTO;

/**
 * REST resource for loading and updating organizational units.
 */
@RequestMapping(value = CustomUnitResource.URI, produces = APPLICATION_JSON_VALUE)
public interface CustomUnitResource {

    /** Resource Mapping URI */
    String URI = "/units";

    /**
     * Retrieve all units for the given type (flat).
     *
     * @param type filter for the unit type
     * @return the unit
     */
    @RequestMapping(method = GET)
    ResponseEntity<CustomUnitListDTO> getUnits(@RequestParam("type") String type);

    /**
     * Retrieve the root unit.
     *
     * @return the unit
     */
    @RequestMapping(value = "/root", method = GET)
    ResponseEntity<CustomUnitDTO> getRoot();

    /**
     * Retrieve the unit with the given key.
     *
     * @param key the unit key
     * @return the unit
     */
    @RequestMapping(value = "/{key}", method = GET)
    ResponseEntity<CustomUnitDTO> getUnit(@PathVariable("key") String key);

    /**
     * Creates a new unit.
     *
     * @param body the body
     * @return the created unit
     */
    @RequestMapping(method = POST)
    ResponseEntity<CustomUnitDTO> create(@RequestBody CustomUnitDTO body);

    /**
     * Updates an existing unit.
     *
     * @param key the unit key
     * @param body the body
     * @return the updated unit
     */
    @RequestMapping(value = "/{key}", method = PUT)
    ResponseEntity<CustomUnitDTO> update(@PathVariable("key") String key, @RequestBody CustomUnitDTO body);

    /**
     * Deletes an existing unit.
     *
     * @param key the unit key
     * @return the deleted unit
     */
    @RequestMapping(value = "/{key}", method = DELETE)
    ResponseEntity<CustomUnitDTO> delete(@PathVariable("key") String key);

    /**
     * Retrieve the participants for the unit with the given key.
     *
     * @param key unique key of this unit
     * @return the list of users
     */
    @RequestMapping(value = "/{key}/users", method = GET)
    ResponseEntity<CustomUnitUserListDTO> getUsersForUnit(@PathVariable("key") String key);

    /**
     * Retrieve the participants for the unit with the given key.
     *
     * @param key unique key of this unit
     * @param body the list of created unit users
     * @return the list of users
     */
    @RequestMapping(value = "/{key}/users", method = POST)
    ResponseEntity<CustomUnitUserListDTO> setUsersForUnit(@RequestBody CustomUnitUserListDTO body, @PathVariable("key") String key);

    /**
     * Removes the participation for the given user.
     *
     * @param key unique key of this unit
     * @param graphId graph id of the user
     * @return nothing
     */
    @RequestMapping(value = "/{key}/users/{gid}", method = DELETE)
    ResponseEntity<Void> deleteUserParticipation(@PathVariable("key") String key, @PathVariable("gid") Long graphId);

    /**
     * Removes the participation for the given user.
     *
     * @param key unique key of this unit
     * @param username username of the user
     * @param graphId graph id of the user
     * @return nothing
     */
    @RequestMapping(value = "/{key}/users", params = {"username"}, method = DELETE)
    ResponseEntity<Void> deleteUserParticipation(@PathVariable("key") String key, @RequestParam("username") String username,
        @RequestParam("gid") Long graphId);

}
