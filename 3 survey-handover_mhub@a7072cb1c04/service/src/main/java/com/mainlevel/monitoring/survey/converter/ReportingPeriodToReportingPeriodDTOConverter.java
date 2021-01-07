/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.converter;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.OrganizationalUnitDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.ReportingPeriodDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.SurveyDTO;
import com.mainlevel.monitoring.survey.database.node.ReportingPeriod;

/**
 * Converter converting {@link ReportingPeriodDTO} to {@link ReportingPeriod}.
 */
@Component
public class ReportingPeriodToReportingPeriodDTOConverter extends AbstractApplicationAwareConverter<ReportingPeriod, ReportingPeriodDTO> {

    @Override
    public ReportingPeriodDTO convert(ReportingPeriod source) {

        OrganizationalUnitDTO unit = null;
        if (source.getUnit() != null) {
            unit = super.getConversionService().convert(source.getUnit(), OrganizationalUnitDTO.class);
        }

        SurveyDTO survey = super.getConversionService().convert(source.getSurvey(), SurveyDTO.class);

        ReportingPeriodDTO period = ReportingPeriodDTO.builder().graphId(source.getGid()).created(source.getCreated()).start(source.getStart())
            .end(source.getEnd()).status(source.getStatus()).survey(survey).unit(unit).build();

        return period;
    }

}
