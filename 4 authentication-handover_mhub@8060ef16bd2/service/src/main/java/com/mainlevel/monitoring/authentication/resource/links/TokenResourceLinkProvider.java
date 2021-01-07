/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.authentication.resource.links;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import com.mainlevel.monitoring.authentication.api.resource.TokenResource;

import lombok.NoArgsConstructor;

/**
 * Link provider for token resource.
 */
@NoArgsConstructor(access = PRIVATE)
public class TokenResourceLinkProvider {

    /**
     * Create link to get token.
     *
     * @return the link builder
     */
    public static ControllerLinkBuilder createLinkToGetToken() {
        return linkTo(methodOn(TokenResource.class).doLogin());
    }

    /**
     * Create link to verify token.
     *
     * @return the link builder
     */
    public static ControllerLinkBuilder createLinkToVerifyToken() {
        return linkTo(methodOn(TokenResource.class).checkIfTokenIsValid());
    }
}
