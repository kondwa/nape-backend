/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.converter;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.participantSession.ParticipantSessionAnswerDTO;
import com.mainlevel.monitoring.survey.api.dto.participantSession.ParticipantSessionQuestionAnswerDTO;
import com.mainlevel.monitoring.survey.database.node.Answer;
import com.mainlevel.monitoring.survey.database.node.AnswerOption;
import com.mainlevel.monitoring.survey.database.node.QuestionAnswer;
import com.mainlevel.monitoring.survey.database.node.question.Question;

/**
 * Converter converting {@link ParticipantSessionAnswerDTO} to {@link Answer}.
 */
@Component
public class ParticipantSessionAnswerDTOToAnswerConverter extends AbstractApplicationAwareConverter<ParticipantSessionAnswerDTO, Answer> {

    @Override
    public Answer convert(ParticipantSessionAnswerDTO source) {

        AnswerOption option = super.getConversionService().convert(source.getOption(), AnswerOption.class);

        QuestionAnswer questionAnswer = convertQuestion(source.getQuestion());

        Answer answer =
            Answer.builder().gid(source.getGraphId()).option(option).question(questionAnswer).type(source.getType()).value(source.getValue()).build();

        questionAnswer.setAnswer(answer);

        return answer;

    }

    private QuestionAnswer convertQuestion(ParticipantSessionQuestionAnswerDTO questionAnswerDTO) {
        Question question = super.getConversionService().convert(questionAnswerDTO.getQuestion(), Question.class);

        QuestionAnswer questionAnswer =
            QuestionAnswer.builder().question(question).title(questionAnswerDTO.getTitle()).order(questionAnswerDTO.getOrder()).build();

        return questionAnswer;
    }

}
