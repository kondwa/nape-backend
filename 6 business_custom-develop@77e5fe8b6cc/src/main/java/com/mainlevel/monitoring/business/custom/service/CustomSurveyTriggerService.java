/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.service;

import com.mainlevel.monitoring.business.custom.resource.dto.survey.CustomSurveyInstanceDTO;

/**
 * Service for applying triggers.
 */
public interface CustomSurveyTriggerService {

    /**
     * Apply all activated triggers on the given survey instance.
     *
     * @param surveyInstance the instance to apply the triggers for
     */
    void applyAllTriggers(CustomSurveyInstanceDTO surveyInstance);

}
