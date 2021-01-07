/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.converter;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.business.custom.resource.dto.survey.CustomSurveyInstanceDTO;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.survey.ReportingPeriodDTO;

/**
 * Converter converting {@link ReportingPeriodDTO} to {@link CustomSurveyInstanceDTO}.
 */
@Component
public class ReportingPeriodDTOToCustomSurveyInstanceDTOConverter extends AbstractApplicationAwareConverter<ReportingPeriodDTO, CustomSurveyInstanceDTO> {

    @Override
    public CustomSurveyInstanceDTO convert(ReportingPeriodDTO source) {

        CustomSurveyInstanceDTO result = CustomSurveyInstanceDTO.builder().instanceId(source.getGraphId()).start(source.getStart()).end(source.getEnd())
            .status(source.getStatus().name()).build();

        if (source.getUnit() != null) {
            result.setUnitName(source.getUnit().getName());
        }

        if (source.getSurvey() != null) {
            result.setTemplateRefId(source.getSurvey().getTemplateRefId());
            result.setTemplateVersion(source.getSurvey().getTemplateVersion());
            result.setTemplateTitle(source.getSurvey().getTemplateTitle());
        }

        return result;
    }

}