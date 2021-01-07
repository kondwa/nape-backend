/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */
package com.mainlevel.monitoring.survey.api.dto.survey;

/**
 * Type indicating what kind of survey is selected.
 */
public enum SurveyType {

    /**
     * Survey instances can be dynamically created.
     */
    AD_HOC,

    /**
     * Survey instances are predefined and cannot be added dynamically.
     */
    PREDEFINED

}
