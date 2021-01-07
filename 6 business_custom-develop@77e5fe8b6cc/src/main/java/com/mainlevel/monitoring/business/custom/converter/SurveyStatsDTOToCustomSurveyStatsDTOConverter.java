/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */
package com.mainlevel.monitoring.business.custom.converter;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.business.custom.resource.dto.survey.CustomSurveyStatsDTO;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.survey.SurveyStatsDTO;

/**
 * Converter converting {@link SurveyStatsDTO} to {@link CustomSurveyStatsDTO}.
 */
@Component
public class SurveyStatsDTOToCustomSurveyStatsDTOConverter extends AbstractApplicationAwareConverter<SurveyStatsDTO, CustomSurveyStatsDTO> {

    @Override
    public CustomSurveyStatsDTO convert(SurveyStatsDTO source) {

        CustomSurveyStatsDTO result = new CustomSurveyStatsDTO();
        result.setGraphId(source.getGraphId());
        result.setSurveyName(source.getSurveyName());

        result.setTemplateRefId(source.getTemplateRefId());
        result.setTemplateName(source.getTemplateName());
        result.setTemplateVersion(source.getTemplateVersion());

        result.setUnit(source.getUnit());
        if (source.getVisibility() != null) {
            result.setVisibility(source.getVisibility().name());
        }
        if (source.getType() != null) {
            result.setType(source.getType().name());
        }

        result.setCreatedBy(source.getCreatedBy());
        result.setCreationDate(source.getCreationDate());

        result.setQuestionGroups(source.getQuestionGroups());
        result.setQuestions(source.getQuestions());
        result.setTargetInstances(source.getTargetInstances());
        result.setTargetPercentage(source.getTargetPercentage());

        result.setInstances(source.getInstances());
        result.setNewInstances(source.getNewInstances());
        result.setNewPercentage(source.getNewPercentage());
        result.setRunningInstances(source.getRunningInstances());
        result.setRunningPercentage(source.getRunningPercentage());
        result.setSubmittedInstances(source.getSubmittedInstances());
        result.setSubmittedPercentage(source.getSubmittedPercentage());

        return result;
    }

}
