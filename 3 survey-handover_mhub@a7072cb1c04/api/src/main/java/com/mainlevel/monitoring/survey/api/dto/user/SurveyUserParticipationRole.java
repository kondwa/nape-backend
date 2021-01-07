/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */
package com.mainlevel.monitoring.survey.api.dto.user;

/**
 * Types for relation between users and nodes.
 */
public enum SurveyUserParticipationRole {

    /**
     * User administrates the node.
     */
    ADMIN,

    /**
     * User manages the node.
     */
    MANAGER,

    /**
     * User participates in the node.
     */
    ASSISTANT,

    /**
     * User owns the node.
     */
    OWNER,

}
