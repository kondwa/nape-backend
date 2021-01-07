/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mainlevel.monitoring.business.custom.service.CustomLinkService;
import com.mainlevel.monitoring.survey.api.dto.SurveyLinkDTO;
import com.mainlevel.monitoring.survey.api.resource.SurveyLinkResource;

/**
 * Default implementation of {@link CustomLinkService}.
 */
@Service
public class CustomLinkServiceImpl implements CustomLinkService {

    private static final char SLASH = '/';

    private static final String EXTERNAL_PREFIX = "e";

    @Autowired
    private SurveyLinkResource linkResource;

    @Override
    public String createHyperlinkFromLink(String protocol, String host, SurveyLinkDTO link) {
        StringBuilder completeLink = new StringBuilder();
        completeLink.append(protocol);
        completeLink.append("://");

        // Workaround for invalid x-forwarded-for header
        if (host.contains(",")) {
            host = host.substring(0, host.indexOf(','));
        }

        completeLink.append(host);
        completeLink.append(SLASH);
        completeLink.append(EXTERNAL_PREFIX);
        completeLink.append(SLASH);
        completeLink.append(link.getToken());
        return completeLink.toString();
    }

    @Override
    public SurveyLinkDTO saveLink(SurveyLinkDTO link) {
        ResponseEntity<SurveyLinkDTO> response = linkResource.createLink(link);

        if (response == null) {
            throw new RuntimeException("Error creating link for survey " + link.getSurvey().getSurveyName() + ".");
        }

        return response.getBody();
    }
}
