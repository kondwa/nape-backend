/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.converter;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.SurveyLinkDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.ReportingPeriodDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.SurveyDTO;
import com.mainlevel.monitoring.survey.api.dto.user.SurveyUserDTO;
import com.mainlevel.monitoring.survey.database.node.Link;

/**
 * Converter converting {@link Link} to {@link SurveyLinkDTO}.
 */
@Component
public class LinkToSurveyLinkDTOConverter extends AbstractApplicationAwareConverter<Link, SurveyLinkDTO> {

    @Override
    public SurveyLinkDTO convert(Link source) {

        SurveyUserDTO user = super.getConversionService().convert(source.getUser(), SurveyUserDTO.class);

        SurveyLinkDTO result = SurveyLinkDTO.builder().graphId(source.getGid()).active(source.isActive()).suffix(source.getSuffix())
            .token(source.getToken()).visibility(source.getVisibility()).owner(source.getOwner()).user(user).build();

        if (source.getSurvey() != null) {
            SurveyDTO survey = super.getConversionService().convert(source.getSurvey(), SurveyDTO.class);
            result.setSurvey(survey);
        }

        if (source.getReportingPeriod() != null) {
            ReportingPeriodDTO period = super.getConversionService().convert(source.getReportingPeriod(), ReportingPeriodDTO.class);
            result.setReportingPeriod(period);
        }

        return result;
    }

}
