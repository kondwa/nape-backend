/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */
package com.mainlevel.monitoring.survey.converter;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.SurveyLinkDTO;
import com.mainlevel.monitoring.survey.database.node.Link;
import com.mainlevel.monitoring.survey.database.node.ReportingPeriod;
import com.mainlevel.monitoring.survey.database.node.Survey;
import com.mainlevel.monitoring.survey.database.node.User;

/**
 * Converter converting {@link SurveyLinkDTO} to {@link Link}.
 */
@Component
public class SurveyLinkDTOToLinkConverter extends AbstractApplicationAwareConverter<SurveyLinkDTO, Link> {

    @Override
    public Link convert(SurveyLinkDTO source) {

        User user = super.getConversionService().convert(source.getUser(), User.class);

        Link result = Link.builder().gid(source.getGraphId()).active(source.isActive()).suffix(source.getSuffix()).visibility(source.getVisibility())
            .expiry(source.getExpiry()).user(user).owner(source.getOwner()).build();

        if (source.getSurvey() != null) {
            Survey survey = super.getConversionService().convert(source.getSurvey(), Survey.class);
            result.setSurvey(survey);
        }

        if (source.getReportingPeriod() != null) {
            ReportingPeriod period = super.getConversionService().convert(source.getReportingPeriod(), ReportingPeriod.class);
            result.setReportingPeriod(period);
        }

        return result;
    }

}
