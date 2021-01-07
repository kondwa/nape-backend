package com.mainlevel.monitoring.authentication.resource.links;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import com.mainlevel.monitoring.authentication.resource.RoleResourceImpl;

import lombok.NoArgsConstructor;

/**
 * Link provider for role resource.
 */
@NoArgsConstructor(access = PRIVATE)
public class RoleResourceLinkProvider {

    /**
     * Create link to get roles.
     *
     * @return the link builder
     */
    public static ControllerLinkBuilder createLinkToGetRoles() {
        return linkTo(methodOn(RoleResourceImpl.class).getRoles());
    }
}
