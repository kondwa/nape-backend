/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.converter;

import java.util.List;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.business.custom.resource.dto.survey.CustomSurveyInstanceDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.survey.CustomSurveyQuestionGroupDTO;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.survey.ParticipantSessionDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.ReportingPeriodDataDTO;

/**
 * Converter converting {@link ParticipantSessionDTO} to {@link CustomSurveyInstanceDTO}.
 */
@Component
public class ReportingPeriodDataDTOToCustomSurveyInstanceDTOConverter
    extends AbstractApplicationAwareConverter<ReportingPeriodDataDTO, CustomSurveyInstanceDTO> {

    @Override
    public CustomSurveyInstanceDTO convert(ReportingPeriodDataDTO source) {

        CustomSurveyInstanceDTO result = null;

        if (source != null) {

            List<CustomSurveyQuestionGroupDTO> questionGroups =
                super.getCollectionConversionService().convert(source.getQuestionGroups(), CustomSurveyQuestionGroupDTO.class);

            result = new CustomSurveyInstanceDTO();
            result.setInstanceId(source.getReportPeriodGid());
            result.setSurveyName(source.getSurveyName());
            result.setSurveyType(source.getSurveyType());
            result.setSurveyVisibilityType(source.getSurveyVisibilityType());
            result.setIntroductionText(source.getIntroductionText());
            result.setClosingText(source.getClosingText());

            result.setTemplateRefId(source.getTemplateId());
            result.setTemplateTitle(source.getTemplateTitle());
            result.setTemplateVersion(source.getTemplateVersion());

            result.setAuthor(source.getAuthor());
            result.setCreationTime(source.getCreationTime());
            result.setLastEditUser(source.getLastEditUser());
            result.setLastEditTime(source.getLastEditTime());

            result.setStart(source.getStart());
            result.setEnd(source.getEnd());
            result.setUnitName(source.getUnitName());
            result.setPages(questionGroups);

            result.setStatus(source.getStatus() != null ? source.getStatus().name() : null);
        }

        return result;
    }

}
