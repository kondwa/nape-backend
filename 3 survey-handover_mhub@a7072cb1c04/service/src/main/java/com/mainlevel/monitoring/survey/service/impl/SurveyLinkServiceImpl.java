/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.service.impl;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mainlevel.monitoring.common.service.AuthenticationAccessService;
import com.mainlevel.monitoring.survey.api.exception.InvalidSurveyLinkException;
import com.mainlevel.monitoring.survey.api.exception.LinkExpiredException;
import com.mainlevel.monitoring.survey.api.exception.TargetEntityNotFound;
import com.mainlevel.monitoring.survey.database.node.Link;
import com.mainlevel.monitoring.survey.database.repository.LinkRepository;
import com.mainlevel.monitoring.survey.service.SurveyLinkService;

/**
 * Default implementation of {@link SurveyLinkService}.
 */
@Service
public class SurveyLinkServiceImpl implements SurveyLinkService {

    private static final int TOKEN_LENGTH = 20;

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private AuthenticationAccessService authenticationAccessService;

    @Override
    @Transactional(propagation = REQUIRED, readOnly = true)
    public Link createLink(Link link) {

        if (link.getUser() == null) {
            throw new InvalidSurveyLinkException(null, "No valid user provided.");
        }

        switch (link.getVisibility()) {

            case ANONYMOUS: {
                if (link.getSurvey() == null) {
                    throw new InvalidSurveyLinkException(link.getUser().getName(), "No valid survey provided for anonymous link.");
                }
                break;
            }

            case PERSONALIZED: {
                if (link.getReportingPeriod() == null) {
                    throw new InvalidSurveyLinkException(link.getUser().getName(), "No valid reporting period provided for personalized link.");
                }
                break;
            }

        }

        String token = RandomStringUtils.randomAlphanumeric(TOKEN_LENGTH);
        link.setToken(token);

        return linkRepository.save(link, 1);
    }

    @Override
    @Transactional(propagation = REQUIRED, readOnly = true)
    public List<Link> loadUserLinks() {
        String username = authenticationAccessService.getCurrentUsername();
        return linkRepository.findAllUserLinks(username);
    }

    @Override
    @Transactional(propagation = REQUIRED, readOnly = true)
    public Link loadLinkByToken(String token) {
        Link link = linkRepository.findByToken(token);

        if (link == null) {
            throw new TargetEntityNotFound(Link.class, token);
        }

        if (!link.isActive()) {
            throw new LinkExpiredException(token);
        }

        if (link.getExpiry().before(new Date())) {
            throw new LinkExpiredException(token);
        }

        return link;
    }

    @Override
    public Link loadLinkForAnonymousSurvey(Long surveyGid) {
        return linkRepository.loadLinkForAnonymousSurvey(surveyGid);
    }

}
