package com.mainlevel.monitoring.authentication.api.resource;

import static com.mainlevel.monitoring.authentication.api.Authentication.SERVICE_NAME;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mainlevel.monitoring.authentication.api.dto.UserDTO;
import com.mainlevel.monitoring.authentication.api.dto.UserListDTO;

/**
 * Endpoint for user management.
 */
@FeignClient(SERVICE_NAME)
public interface UserResource {

    /** Resource Mapping URI */
    String URI = "/users";

    /**
     * Loads the list of all users.
     *
     * @param username a username filter
     * @return the paged resource
     */
    @RequestMapping(value = URI, method = GET, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<UserListDTO> getUsers(@RequestParam(name = "username", required = false) String username);

    /**
     * Load all information for the user with the given id.
     *
     * @param id the user id
     * @return the user
     */
    @RequestMapping(value = URI + "/{id}", method = GET, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<UserDTO> getUser(@PathVariable("id") Long id);

    /**
     * Load all information for the current user.
     *
     * @return the user
     */
    @RequestMapping(value = URI + "/current", method = GET, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<UserDTO> getCurrentUser();

    /**
     * Creates a new user in the database.
     *
     * @param userDTO the user information to store
     * @return the created user
     */
    @RequestMapping(value = URI, method = POST, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO);

    /**
     * Update the user with the given identifier.
     *
     * @param identifier user id
     * @param userDTO user information to update
     * @return the updated user
     */
    @RequestMapping(value = URI + "/{identifier}", method = PUT, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<UserDTO> updateUser(@PathVariable("identifier") Long identifier, @RequestBody UserDTO userDTO);

}
