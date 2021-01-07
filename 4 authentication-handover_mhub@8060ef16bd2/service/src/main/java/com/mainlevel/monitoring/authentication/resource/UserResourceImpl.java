package com.mainlevel.monitoring.authentication.resource;

import static com.mainlevel.monitoring.common.constant.RoleConstant.ROLE_USER;
import static org.springframework.http.HttpStatus.CREATED;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mainlevel.monitoring.authentication.api.dto.UserDTO;
import com.mainlevel.monitoring.authentication.api.dto.UserListDTO;
import com.mainlevel.monitoring.authentication.api.resource.UserResource;
import com.mainlevel.monitoring.authentication.resource.links.UserResourceLinkProvider;
import com.mainlevel.monitoring.authentication.service.UserService;
import com.mainlevel.monitoring.common.monitoring.annotation.PerformanceMonitor;
import com.mainlevel.monitoring.common.service.AuthenticationAccessService;

import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * Endpoint for user management.
 */
@Slf4j
@RestController
@Secured(ROLE_USER)
public class UserResourceImpl implements UserResource {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationAccessService authenticationAccessService;

    @Override
    @PerformanceMonitor
    public ResponseEntity<UserListDTO> getUsers(@RequestParam(name = "username", required = false) String username) {

        List<UserDTO> users = Collections.emptyList();
        if (username != null) {
            log.info("Requesting user with username {}.", username);
            UserDTO userDTO = userService.getUser(username);
            if (userDTO != null) {
                userDTO.add(UserResourceLinkProvider.createLinkToGetUser(userDTO.getIdentifier()).withSelfRel());
                users = Arrays.asList(userDTO);
            }
        } else {
            log.info("Requesting all Users.");
            users = userService.getUsers();
        }

        UserListDTO result = UserListDTO.builder().users(users).build();

        return ResponseEntity.ok(result);
    }

    @Override
    @PerformanceMonitor
    public ResponseEntity<UserDTO> createUser(
        @ApiParam(value = "UserDTO containing the informations for a new user.") @Valid @RequestBody final UserDTO userDTO) {

        log.info("Created a new user {}.", userDTO.getUsername());

        final UserDTO resultUser = userService.createUser(userDTO);
        userDTO.add(UserResourceLinkProvider.createLinkToGetUser(resultUser.getIdentifier()).withSelfRel());
        return ResponseEntity.status(CREATED).body(resultUser);
    }

    @Override
    @PerformanceMonitor
    public ResponseEntity<UserDTO> updateUser(
        @ApiParam(value = "Id uniquely determinates the user.") @PathVariable("identifier") final Long identifier,
        @ApiParam(value = "UserDTO containing the update informations.") @RequestBody final UserDTO userDTO) {

        log.info("Updating user {}.", userDTO.getUsername());

        final UserDTO resultUser = userService.updateUser(identifier, userDTO);
        resultUser.add(UserResourceLinkProvider.createLinkToGetUser(resultUser.getIdentifier()).withSelfRel());
        return ResponseEntity.ok(resultUser);
    }

    @Override
    @PerformanceMonitor
    public ResponseEntity<UserDTO> getUser(@ApiParam(value = "Id uniquely determinates the user.") @PathVariable final Long id) {

        log.debug("Requesting user with id: {}", id);

        final UserDTO userDTO = userService.getUser(id);
        userDTO.add(UserResourceLinkProvider.createLinkToGetUser(userDTO.getIdentifier()).withSelfRel());
        return ResponseEntity.ok(userDTO);
    }

    @Override
    @PerformanceMonitor
    public ResponseEntity<UserDTO> getCurrentUser() {
        final UserDTO userDTO = userService.getUser(authenticationAccessService.getCurrentUserId());

        log.info("{} wants to know who he is.", userDTO.getUsername());

        userDTO.add(UserResourceLinkProvider.createLinkToGetUser(userDTO.getIdentifier()).withSelfRel());
        return ResponseEntity.ok(userDTO);
    }

}
