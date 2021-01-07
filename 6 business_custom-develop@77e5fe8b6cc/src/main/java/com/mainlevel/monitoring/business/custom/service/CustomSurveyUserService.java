/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.service;

import com.mainlevel.monitoring.survey.api.dto.user.SurveyUserDTO;

/**
 * Service for creating survey users.
 */
public interface CustomSurveyUserService {

    /**
     * Find a given user for the given username.
     *
     * @param username name of the survey user
     * @return the survey user
     */
    SurveyUserDTO findSurveyUser(String username);

    /**
     * Creates a survey user based on a custom temporary user.
     *
     * @param surveyUser the custom user
     * @return the created survey user
     */
    SurveyUserDTO createSurveyUser(SurveyUserDTO surveyUser);

    /**
     * Updates a survey user based on a custom temporary user.
     *
     * @param user the custom user
     * @return the created survey user
     */
    SurveyUserDTO updateSurveyUser(SurveyUserDTO user);

}
