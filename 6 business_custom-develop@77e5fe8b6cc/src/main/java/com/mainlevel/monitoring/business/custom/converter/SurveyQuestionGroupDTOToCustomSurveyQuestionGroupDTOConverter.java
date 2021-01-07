/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.converter;

import java.util.List;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.admin.api.dto.template.PageDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.question.CustomQuestionDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.survey.CustomSurveyQuestionGroupDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.template.CustomTemplateQuestionGroupDTO;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.survey.question.SurveyQuestionGroupDTO;

/**
 * Converter converting {@link PageDTO} to {@link CustomTemplateQuestionGroupDTO}.
 */
@Component
public class SurveyQuestionGroupDTOToCustomSurveyQuestionGroupDTOConverter
    extends AbstractApplicationAwareConverter<SurveyQuestionGroupDTO, CustomSurveyQuestionGroupDTO> {

    @Override
    public CustomSurveyQuestionGroupDTO convert(SurveyQuestionGroupDTO source) {

        List<CustomQuestionDTO> questions = super.getCollectionConversionService().convert(source.getQuestions(), CustomQuestionDTO.class);
        questions.sort((CustomQuestionDTO q1, CustomQuestionDTO q2) -> q1.getIndex().compareTo(q2.getIndex()));

        CustomSurveyQuestionGroupDTO result = CustomSurveyQuestionGroupDTO.builder().graphId(source.getGraphId()).name(source.getName())
            .index(source.getIndex()).description(source.getDescription()).visible(source.getVisible()).questions(questions).build();

        return result;
    }

}
