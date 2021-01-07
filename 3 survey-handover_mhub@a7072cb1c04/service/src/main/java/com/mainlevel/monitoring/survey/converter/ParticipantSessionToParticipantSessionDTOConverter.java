/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.participantSession.ParticipantSessionAnswerDTO;
import com.mainlevel.monitoring.survey.api.dto.participantSession.ParticipantSessionQuestionAnswerDTO;
import com.mainlevel.monitoring.survey.api.dto.participantSession.ParticipantSessionQuestionDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.ParticipantSessionDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.ReportingPeriodDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.question.AnswerOptionDTO;
import com.mainlevel.monitoring.survey.database.node.Answer;
import com.mainlevel.monitoring.survey.database.node.ParticipantSession;
import com.mainlevel.monitoring.survey.database.node.QuestionAnswer;

/**
 * Converter converting {@link ParticipantSession} to {@link ParticipantSessionDTO}.
 */
@Component
public class ParticipantSessionToParticipantSessionDTOConverter extends AbstractApplicationAwareConverter<ParticipantSession, ParticipantSessionDTO> {

    @Override
    public ParticipantSessionDTO convert(ParticipantSession source) {

        if (source != null) {

            ReportingPeriodDTO period = super.getConversionService().convert(source.getPeriod(), ReportingPeriodDTO.class);

            List<ParticipantSessionAnswerDTO> answers = convertAnswers(source.getAnswers());
            String username = source.getUser() != null ? source.getUser().getName() : "undefined";

            ParticipantSessionDTO result =
                ParticipantSessionDTO.builder().graphId(source.getGid()).changeByUser(username).reportingPeriod(period).answers(answers).build();
            return result;
        }

        return null;
    }

    private List<ParticipantSessionAnswerDTO> convertAnswers(List<Answer> answers) {
        List<ParticipantSessionAnswerDTO> answerDTOs = new ArrayList<>();

        if (answers != null) {
            answers.forEach(answer -> {

                QuestionAnswer questionAnswer = answer.getQuestion();

                ParticipantSessionQuestionDTO questionDTO =
                    super.getConversionService().convert(questionAnswer.getQuestion(), ParticipantSessionQuestionDTO.class);
                AnswerOptionDTO optionDTO = super.getConversionService().convert(answer.getOption(), AnswerOptionDTO.class);

                ParticipantSessionQuestionAnswerDTO questionAnswerDTO = ParticipantSessionQuestionAnswerDTO.builder().order(questionAnswer.getOrder())
                    .title(questionAnswer.getTitle()).question(questionDTO).build();

                ParticipantSessionAnswerDTO answerDTO = ParticipantSessionAnswerDTO.builder().graphId(answer.getGid()).option(optionDTO)
                    .type(answer.getType()).value(answer.getValue()).question(questionAnswerDTO).build();

                answerDTOs.add(answerDTO);
            });
        }

        return answerDTOs;
    }

}
