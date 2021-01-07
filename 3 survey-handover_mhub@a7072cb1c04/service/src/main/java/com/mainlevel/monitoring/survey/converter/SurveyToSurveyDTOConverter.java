/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.converter;

import java.util.List;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.OrganizationalUnitDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.SurveyDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.question.SurveyQuestionGroupDTO;
import com.mainlevel.monitoring.survey.database.node.Survey;

/**
 * Converter converting {@link Survey} node to {@link SurveyDTO}.
 */
@Component
public class SurveyToSurveyDTOConverter extends AbstractApplicationAwareConverter<Survey, SurveyDTO> {

    @Override
    public SurveyDTO convert(Survey source) {

        List<SurveyQuestionGroupDTO> groups = super.getCollectionConversionService().convert(source.getGroups(), SurveyQuestionGroupDTO.class);

        OrganizationalUnitDTO unit =
            source.getUnit() != null ? super.getConversionService().convert(source.getUnit(), OrganizationalUnitDTO.class) : null;

        SurveyDTO surveyDTO = SurveyDTO.builder().graphId(source.getGid()).creationTime(source.getCreationTime()).surveyName(source.getName())
            .templateRefId(source.getTemplateRefId()).templateVersion(source.getTemplateVersion()).type(source.getType())
            .introductionText(source.getIntroductionText()).closingText(source.getClosingText()).visibility(source.getVisibility())
            .templateTitle(source.getTemplateTitle()).unit(unit).status(source.getStatus()).questionGroups(groups)
            .targetInstances(source.getTargetInstances()).build();

        return surveyDTO;
    }

}
