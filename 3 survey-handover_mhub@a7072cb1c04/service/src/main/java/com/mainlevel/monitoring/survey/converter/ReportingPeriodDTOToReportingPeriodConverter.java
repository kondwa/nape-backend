/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.converter;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.survey.ReportingPeriodDTO;
import com.mainlevel.monitoring.survey.database.node.Client;
import com.mainlevel.monitoring.survey.database.node.OrganizationalUnit;
import com.mainlevel.monitoring.survey.database.node.ReportingPeriod;
import com.mainlevel.monitoring.survey.database.node.Survey;

/**
 * Converter converting {@link ReportingPeriodDTO} to {@link ReportingPeriod}.
 */
@Component
public class ReportingPeriodDTOToReportingPeriodConverter extends AbstractApplicationAwareConverter<ReportingPeriodDTO, ReportingPeriod> {

    @Override
    public ReportingPeriod convert(ReportingPeriodDTO source) {

        OrganizationalUnit unit = null;
        if (source.getUnit() != null) {
            unit = super.getConversionService().convert(source.getUnit(), OrganizationalUnit.class);
        }
        Client client = null;
        if (source.getClient() != null) {
            client = super.getConversionService().convert(source.getClient(), Client.class);
        }

        Survey survey = super.getConversionService().convert(source.getSurvey(), Survey.class);

        ReportingPeriod period = ReportingPeriod.builder().gid(source.getGraphId()).start(source.getStart()).end(source.getEnd())
            .status(source.getStatus()).created(source.getCreated()).client(client).survey(survey).unit(unit).
            submissionTimestamp(source.getSubmissionTimestamp()).foreignSurveyId(source.getForeignSurveyId()).build();

        return period;
    }

}
