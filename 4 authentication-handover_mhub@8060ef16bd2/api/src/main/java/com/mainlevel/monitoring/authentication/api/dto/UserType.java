/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.authentication.api.dto;

/**
 * Type of user.
 */
public enum UserType {

    /**
     * Internally stored user.
     */
    INTERNAL,

    /**
     * User that is only available for a period of time.
     */
    TEMPORARY,

    /**
     * User that is stored in an external system.
     */
    EXTERNAL

}
