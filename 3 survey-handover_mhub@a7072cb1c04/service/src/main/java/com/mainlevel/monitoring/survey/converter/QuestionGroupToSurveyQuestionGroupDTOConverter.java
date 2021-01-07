/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.converter;

import java.util.List;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.survey.question.SurveyQuestionDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.question.SurveyQuestionGroupDTO;
import com.mainlevel.monitoring.survey.database.node.QuestionGroup;

/**
 * Converter converting {@link QuestionGroup} to {@link SurveyQuestionGroupDTO}.
 */
@Component
public class QuestionGroupToSurveyQuestionGroupDTOConverter extends AbstractApplicationAwareConverter<QuestionGroup, SurveyQuestionGroupDTO> {

    @Override
    public SurveyQuestionGroupDTO convert(QuestionGroup source) {

        List<SurveyQuestionDTO> questions = super.getCollectionConversionService().convert(source.getQuestions(), SurveyQuestionDTO.class);

        SurveyQuestionGroupDTO result = SurveyQuestionGroupDTO.builder().graphId(source.getGid()).name(source.getName()).index(source.getIndex())
            .visible(source.getVisible()).description(source.getDescription()).questions(questions).build();

        return result;
    }

}
