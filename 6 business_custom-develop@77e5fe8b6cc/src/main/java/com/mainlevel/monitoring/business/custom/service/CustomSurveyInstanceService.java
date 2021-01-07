/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.service;

import com.mainlevel.monitoring.business.custom.resource.dto.survey.CustomSurveyInstanceDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.ReportingPeriodDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.SurveyDTO;

/**
 * Service for survey instnce interaction.
 */
public interface CustomSurveyInstanceService {

    /**
     * Creates a new survey instance for the given body.
     *
     * @param ipAddress client ip address
     * @param survey survey instance body
     * @return created survey instance
     */
    ReportingPeriodDTO createSurveyInstance(String ipAddress, SurveyDTO survey);

    /**
     * Update the given survey instance as survey reporting period.
     *
     * @param surveyInstanceGid survey instance graph id
     * @param ipAddress client ip address
     * @param body survey instance body
     * @return updated reporting period
     */
    ReportingPeriodDTO updateSurveyInstance(Long surveyInstanceGid, String ipAddress, CustomSurveyInstanceDTO body);

}
