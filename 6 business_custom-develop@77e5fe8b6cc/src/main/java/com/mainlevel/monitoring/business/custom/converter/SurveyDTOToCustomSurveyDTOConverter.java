/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.converter;

import java.util.List;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.business.custom.resource.dto.survey.CustomSurveyDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.survey.CustomSurveyQuestionGroupDTO;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.survey.SurveyDTO;

/**
 * Converter convertign {@link SurveyDTO} to {@link CustomSurveyDTO}.
 */
@Component
public class SurveyDTOToCustomSurveyDTOConverter extends AbstractApplicationAwareConverter<SurveyDTO, CustomSurveyDTO> {

    @Override
    public CustomSurveyDTO convert(SurveyDTO source) {

        String unitName = source.getUnit() != null ? source.getUnit().getName() : null;

        List<CustomSurveyQuestionGroupDTO> questionGroups =
            super.getCollectionConversionService().convert(source.getQuestionGroups(), CustomSurveyQuestionGroupDTO.class);

        questionGroups.sort((CustomSurveyQuestionGroupDTO g1, CustomSurveyQuestionGroupDTO g2) -> g1.getIndex().compareTo(g2.getIndex()));

        CustomSurveyDTO result = CustomSurveyDTO.builder().graphId(source.getGraphId()).surveyName(source.getSurveyName())
            .templateRefId(source.getTemplateRefId()).templateVersion(source.getTemplateVersion()).templateTitle(source.getTemplateTitle())
            .type(source.getType()).creationTime(source.getCreationTime()).status(source.getStatus()).visibility(source.getVisibility())
            .unitName(unitName).targetInstances(source.getTargetInstances()).questionGroups(questionGroups).build();

        return result;
    }

}
