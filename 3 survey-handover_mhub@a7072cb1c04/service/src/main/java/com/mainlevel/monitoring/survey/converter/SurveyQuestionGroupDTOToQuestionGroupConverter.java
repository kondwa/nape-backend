/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.converter;

import java.util.List;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.survey.question.SurveyQuestionGroupDTO;
import com.mainlevel.monitoring.survey.database.node.QuestionGroup;
import com.mainlevel.monitoring.survey.database.node.question.Question;

/**
 * Converter converting {@link SurveyQuestionGroupDTO} to {@link QuestionGroup}.
 */
@Component
public class SurveyQuestionGroupDTOToQuestionGroupConverter extends AbstractApplicationAwareConverter<SurveyQuestionGroupDTO, QuestionGroup> {

    @Override
    public QuestionGroup convert(SurveyQuestionGroupDTO source) {

        List<Question> questions = super.getCollectionConversionService().convert(source.getQuestions(), Question.class);
        QuestionGroup result = QuestionGroup.builder().gid(source.getGraphId()).name(source.getName()).index(source.getIndex())
            .description(source.getDescription()).visible(source.getVisible()).questions(questions).build();

        return result;
    }

}
