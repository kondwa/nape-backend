/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.converter;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.business.custom.resource.dto.survey.CustomSurveyInstanceDTO;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.survey.ReportingPeriodOverviewDTO;

/**
 * Converter converting {@link ReportingPeriodOverviewDTO} to {@link CustomSurveyInstanceDTO}.
 */
@Component
public class ReportingPeriodOverviewDTOToCustomSurveyInstanceDTOConverter
    extends AbstractApplicationAwareConverter<ReportingPeriodOverviewDTO, CustomSurveyInstanceDTO> {

    @Override
    public CustomSurveyInstanceDTO convert(ReportingPeriodOverviewDTO source) {

        CustomSurveyInstanceDTO result = CustomSurveyInstanceDTO.builder().instanceId(source.getReportPeriodGid()).author(source.getAuthor())
            .owner(source.getOwner()).originalOwner(source.getOriginalOwner()).creationTime(source.getCreationTime())
            .templateRefId(source.getTemplateId()).templateTitle(source.getTemplateTitle()).templateVersion(source.getTemplateVersion())
            .unitName(source.getUnitName()).start(source.getStart()).end(source.getEnd()).lastEditTime(source.getLastEditTime())
            .lastEditUser(source.getLastEditUser()).lastEditAddress(source.getLastEditAddress()).lastEditClientType(source.getLastEditClientType())
            .status(source.getStatus()).questionsAnswered(source.getQuestionsAnswered()).questionsRequired(source.getQuestionsRequired())
            .questionsTotal(source.getQuestionsTotal()).build();

        return result;
    }

}
