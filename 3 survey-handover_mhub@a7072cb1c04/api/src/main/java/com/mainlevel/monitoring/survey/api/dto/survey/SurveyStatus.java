/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.api.dto.survey;

/**
 * Enumeration defining the status of a survey.
 */
public enum SurveyStatus {

    /**
     * Survey has been started and is ready to get processed.
     */
    ACTIVE,

    /**
     * Survey is currently not available for creating new instances, but old instaces can be finished.
     */
    INACTIVE,

    /**
     * Survey is closed and cannot be processed.
     */
    CLOSED

}
