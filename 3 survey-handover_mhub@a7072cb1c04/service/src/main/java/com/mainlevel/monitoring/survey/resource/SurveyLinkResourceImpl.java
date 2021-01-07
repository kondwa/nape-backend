/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */
package com.mainlevel.monitoring.survey.resource;

import static com.mainlevel.monitoring.common.constant.RoleConstant.ROLE_ANONYMOUS;
import static com.mainlevel.monitoring.common.constant.RoleConstant.ROLE_USER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mainlevel.monitoring.survey.api.dto.SurveyLinkDTO;
import com.mainlevel.monitoring.survey.api.resource.SurveyLinkResource;
import com.mainlevel.monitoring.survey.database.node.Link;
import com.mainlevel.monitoring.survey.service.SurveyLinkService;

/**
 * Default implementation of {@link SurveyLinkResource}.
 */
@RestController
public class SurveyLinkResourceImpl implements SurveyLinkResource {

    @Autowired
    private SurveyLinkService linkService;

    @Autowired
    private ConversionService conversionService;

    @Override
    @Secured({ROLE_USER, ROLE_ANONYMOUS})
    public ResponseEntity<SurveyLinkDTO> getLink(@PathVariable(name = "linkToken", required = true) String token) {

        Link link = linkService.loadLinkByToken(token);

        SurveyLinkDTO result = conversionService.convert(link, SurveyLinkDTO.class);

        return ResponseEntity.ok(result);
    }

    @Override
    @Secured(ROLE_USER)
    public ResponseEntity<SurveyLinkDTO> createLink(@RequestBody SurveyLinkDTO linkDTO) {

        Link link = conversionService.convert(linkDTO, Link.class);
        link = linkService.createLink(link);

        SurveyLinkDTO result = conversionService.convert(link, SurveyLinkDTO.class);

        return ResponseEntity.ok(result);
    }

}
