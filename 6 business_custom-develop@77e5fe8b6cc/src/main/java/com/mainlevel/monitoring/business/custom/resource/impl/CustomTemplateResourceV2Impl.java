/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.resource.impl;

import static com.mainlevel.monitoring.common.constant.RoleConstant.ROLE_USER;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mainlevel.monitoring.admin.api.dto.template.SurveyTemplateDTO;
import com.mainlevel.monitoring.admin.api.dto.template.SurveyTemplateListDTO;
import com.mainlevel.monitoring.admin.api.resource.TemplateResource;
import com.mainlevel.monitoring.business.custom.resource.CustomTemplateResource;
import com.mainlevel.monitoring.business.custom.resource.CustomTemplateResourceV2;
import com.mainlevel.monitoring.business.custom.resource.dto.template.CustomTemplateDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.template.CustomTemplateListDTO;
import com.mainlevel.monitoring.common.service.CollectionConversionService;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of {@link CustomTemplateResource}.
 */
@Slf4j
@RestController
@Secured(ROLE_USER)
public class CustomTemplateResourceV2Impl implements CustomTemplateResourceV2 {

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private CollectionConversionService collectionConversionService;

    @Autowired
    private TemplateResource templateClient;

    @Override
    public ResponseEntity<CustomTemplateListDTO> loadTemplates() {

        log.info("Load the list of CUSTOM templates.");

        ResponseEntity<SurveyTemplateListDTO> response = templateClient.getSurveyTemplates(null);

        List<CustomTemplateDTO> templates = collectionConversionService.convert(response.getBody().getSurveys(), CustomTemplateDTO.class);

        CustomTemplateListDTO templateList = CustomTemplateListDTO.builder().templates(templates).build();

        return ResponseEntity.ok(templateList);
    }

    @Override
    public ResponseEntity<CustomTemplateDTO> getSurveyByVersion(@PathVariable("identifier") String identifier, @PathVariable("version") Long version) {

        log.info("Load the CUSTOM template {} for version {}.", identifier, version);

        ResponseEntity<SurveyTemplateDTO> response = templateClient.getSurveyByVersion(identifier, version);

        CustomTemplateDTO template = conversionService.convert(response.getBody(), CustomTemplateDTO.class);

        return ResponseEntity.ok(template);
    }

}
