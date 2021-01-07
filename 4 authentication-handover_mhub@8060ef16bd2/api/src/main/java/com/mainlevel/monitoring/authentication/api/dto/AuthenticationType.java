/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.authentication.api.dto;

/**
 * Enum defining on which kind a user will login.
 */
public enum AuthenticationType {

    /**
     * User logs in via username and password.
     */
    LOGIN,

    /**
     * User logs in via permanent link.
     */
    LINK

}
