/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mainlevel.monitoring.business.custom.service.CustomSurveyUserService;
import com.mainlevel.monitoring.survey.api.dto.user.SurveyUserDTO;
import com.mainlevel.monitoring.survey.api.resource.SurveyUserResource;

/**
 * Default implementation of {@link CustomSurveyUserService}.
 */
@Service
public class CustomSurveyUserServiceImpl implements CustomSurveyUserService {

    @Autowired
    private SurveyUserResource surveyUserResource;

    @Override
    public SurveyUserDTO findSurveyUser(String username) {
        ResponseEntity<SurveyUserDTO> response = surveyUserResource.findUserByUsername(username);
        return response.getBody();
    }

    @Override
    public SurveyUserDTO createSurveyUser(SurveyUserDTO surveyUser) {
        ResponseEntity<SurveyUserDTO> response = surveyUserResource.createUser(surveyUser);
        return response.getBody();
    }

    @Override
    public SurveyUserDTO updateSurveyUser(SurveyUserDTO surveyUser) {
        ResponseEntity<SurveyUserDTO> response = surveyUserResource.updateUser(surveyUser.getGraphId(), surveyUser);
        return response.getBody();
    }

}
