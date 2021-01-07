/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.api.dto.user;

/**
 * Type of users.
 */
public enum SurveyUserType {

    /**
     * Internally stored user.
     */
    INTERNAL,

    /**
     * User that is only available for a period of time.
     */
    TEMPORARY,

    /**
     * Anonymous user.
     */
    ANONYMOUS

}
