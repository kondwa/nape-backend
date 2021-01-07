/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.converter;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.survey.answer.SurveyAnswerDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.question.AnswerOptionDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.question.SurveyQuestionAnswerDTO;
import com.mainlevel.monitoring.survey.database.node.Answer;
import com.mainlevel.monitoring.survey.database.node.QuestionAnswer;

/**
 * Converter converting {@link Answer} to {@link SurveyQuestionAnswerDTO}.
 */
@Component
public class AnswerToSurveyQuestionAnswerDTOConverter extends AbstractApplicationAwareConverter<Answer, SurveyQuestionAnswerDTO> {

    @Override
    public SurveyQuestionAnswerDTO convert(Answer source) {

        QuestionAnswer questionAnswer = source.getQuestion();

        AnswerOptionDTO option = source.getOption() != null ? super.getConversionService().convert(source.getOption(), AnswerOptionDTO.class) : null;

        SurveyAnswerDTO answerDTO =
            SurveyAnswerDTO.builder().graphId(source.getGid()).value(source.getValue()).option(option).type(source.getType()).build();

        SurveyQuestionAnswerDTO result =
            SurveyQuestionAnswerDTO.builder().order(questionAnswer.getOrder()).title(questionAnswer.getTitle()).answer(answerDTO).build();

        return result;
    }

}
