/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.resource.impl;

import static com.mainlevel.monitoring.common.constant.RoleConstant.ROLE_USER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mainlevel.monitoring.admin.api.dto.template.SurveyTemplateDTO;
import com.mainlevel.monitoring.admin.api.dto.template.SurveyTemplateListDTO;
import com.mainlevel.monitoring.admin.api.resource.TemplateResource;
import com.mainlevel.monitoring.business.custom.resource.CustomTemplateResource;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of {@link CustomTemplateResource}.
 */
@Slf4j
@RestController
@Secured(ROLE_USER)
public class CustomTemplateResourceImpl implements CustomTemplateResource {

    @Autowired
    private TemplateResource templateClient;

    @Override
    public ResponseEntity<SurveyTemplateListDTO> loadCustomTemplates() {

        log.info("Load the list of CUSTOM templates.");

        ResponseEntity<SurveyTemplateListDTO> surveyTemplates = templateClient.getSurveyTemplates("59b25aa49db462f0cc4d18a2");

        return surveyTemplates;
    }
    
    @Override
    public ResponseEntity<SurveyTemplateListDTO> loadTemplates() {

        log.info("Load the list of all templates.");

        ResponseEntity<SurveyTemplateListDTO> surveyTemplates = templateClient.getSurveyTemplates(null);

        return surveyTemplates;
    }

    @Override
    public ResponseEntity<SurveyTemplateDTO> getSurveyByVersion(@PathVariable("identifier") String identifier,
        @PathVariable("version") Long version) {

        log.info("Load the CUSTOM template {} for version {}.", identifier, version);

        ResponseEntity<SurveyTemplateDTO> surveyTemplate = templateClient.getSurveyByVersion(identifier, version);

        return surveyTemplate;
    }

}
