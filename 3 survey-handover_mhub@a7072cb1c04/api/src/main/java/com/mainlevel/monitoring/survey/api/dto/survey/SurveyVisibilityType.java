/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.api.dto.survey;

/**
 * Type of survey visibility.
 */
public enum SurveyVisibilityType {

    /**
     * Survey is creatable by anyone.
     */
    ANONYMOUS,

    /**
     * Survey is only editable by a logged in user.
     */
    PERSONALIZED

}
