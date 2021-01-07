package com.mainlevel.monitoring.authentication.resource.links;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import com.mainlevel.monitoring.authentication.api.dto.UserDTO;
import com.mainlevel.monitoring.authentication.resource.UserResourceImpl;

import lombok.NoArgsConstructor;

/**
 * Link provider for user resource.
 */
@NoArgsConstructor(access = PRIVATE)
public class UserResourceLinkProvider {

    /**
     * Create link to get specific user.
     *
     * @param identifier user id
     * @return the link builder
     */
    public static ControllerLinkBuilder createLinkToGetUser(final Long identifier) {
        return linkTo(methodOn(UserResourceImpl.class).getUser(identifier));
    }

    /**
     * Create link to get current user.
     *
     * @return the link builder
     */
    public static ControllerLinkBuilder createLinkToGetCurrentUser() {
        return linkTo(methodOn(UserResourceImpl.class).getCurrentUser());
    }

    /**
     * Create link to create user.
     *
     * @param userDTO user body
     * @return the link builder
     */
    public static ControllerLinkBuilder createLinkToCreateUser(final UserDTO userDTO) {
        return linkTo(methodOn(UserResourceImpl.class).createUser(userDTO));
    }

    /**
     * Create link to update user.
     *
     * @param identifier user id
     * @param userDTO user body
     * @return the link builder
     */
    public static ControllerLinkBuilder createLinkToUpdateUser(final Long identifier, final UserDTO userDTO) {
        return linkTo(methodOn(UserResourceImpl.class).updateUser(identifier, userDTO));
    }

    /**
     * Create link to get users (pageable).
     *
     * @return the link builder
     */
    public static ControllerLinkBuilder createLinkToGetUsers(String username) {
        return linkTo(methodOn(UserResourceImpl.class).getUsers(username));
    }
}
