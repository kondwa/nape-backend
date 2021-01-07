/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.converter;

import java.util.List;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.survey.SurveyDTO;
import com.mainlevel.monitoring.survey.database.node.OrganizationalUnit;
import com.mainlevel.monitoring.survey.database.node.QuestionGroup;
import com.mainlevel.monitoring.survey.database.node.Survey;

/**
 * Converter converting survey DTO to survey node.
 */
@Component
public class SurveyDTOToSurveyConverter extends AbstractApplicationAwareConverter<SurveyDTO, Survey> {

    @Override
    public Survey convert(SurveyDTO source) {

        List<QuestionGroup> groups = super.getCollectionConversionService().convert(source.getQuestionGroups(), QuestionGroup.class);

        OrganizationalUnit unit = source.getUnit() != null ? super.getConversionService().convert(source.getUnit(), OrganizationalUnit.class) : null;

        Survey surveyNode = Survey.builder().gid(source.getGraphId()).name(source.getSurveyName()).templateRefId(source.getTemplateRefId())
            .templateVersion(source.getTemplateVersion()).templateTitle(source.getTemplateTitle()).type(source.getType())
            .introductionText(source.getIntroductionText()).closingText(source.getClosingText()).creationTime(source.getCreationTime())
            .visibility(source.getVisibility()).unit(unit).status(source.getStatus()).groups(groups).targetInstances(source.getTargetInstances())
            .build();

        return surveyNode;
    }

}
