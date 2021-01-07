/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import com.mainlevel.monitoring.business.custom.resource.dto.answer.CustomAnswerDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.answer.CustomQuestionAnswerDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.question.CustomQuestionDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.survey.CustomSurveyInstanceDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.survey.CustomSurveyQuestionGroupDTO;
import com.mainlevel.monitoring.business.custom.service.CustomSurveyInstanceService;
import com.mainlevel.monitoring.business.custom.service.CustomSurveyService;
import com.mainlevel.monitoring.survey.api.dto.ClientDTO;
import com.mainlevel.monitoring.survey.api.dto.ClientType;
import com.mainlevel.monitoring.survey.api.dto.participantSession.ParticipantSessionAnswerDTO;
import com.mainlevel.monitoring.survey.api.dto.participantSession.ParticipantSessionQuestionAnswerDTO;
import com.mainlevel.monitoring.survey.api.dto.participantSession.ParticipantSessionQuestionDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.ParticipantSessionDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.ReportingPeriodDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.ReportingPeriodStatus;
import com.mainlevel.monitoring.survey.api.dto.survey.SurveyDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.question.AnswerOptionDTO;

/**
 * Default implementation of {@link CustomSurveyInstanceService}.
 */
@Service
public class CustomSurveyInstanceServiceImpl implements CustomSurveyInstanceService {

    @Autowired
    private CustomSurveyService customSurveyService;

    @Autowired
    private ConversionService conversionService;

    @Override
    public ReportingPeriodDTO createSurveyInstance(String ipAddress, SurveyDTO survey) {
        ClientDTO client = ClientDTO.builder().address(ipAddress).type(ClientType.WEB).build();

        Date now = new Date();
        ReportingPeriodDTO period =
            ReportingPeriodDTO.builder().status(ReportingPeriodStatus.NEW).start(now).end(now).survey(survey).client(client).build();

        period = customSurveyService.createReportingPeriod(period);
        return period;
    }

    @Override
    public ReportingPeriodDTO updateSurveyInstance(final Long surveyInstanceGid, String ipAddress, CustomSurveyInstanceDTO body) {
        ReportingPeriodDTO period = customSurveyService.loadReportingPeriod(surveyInstanceGid);

        if (period.getStatus() == ReportingPeriodStatus.SENT) {
            throw new IllegalStateException("Survey instance is already submitted!");
        }

        List<ParticipantSessionAnswerDTO> answers = mapAnswers(body);

        ClientDTO client = ClientDTO.builder().address(ipAddress).type(ClientType.WEB).build();

        ParticipantSessionDTO session = ParticipantSessionDTO.builder().reportingPeriod(period).client(client).answers(answers).build();
        session = customSurveyService.saveParticipantSession(session, ipAddress);

        ReportingPeriodStatus status =
            ReportingPeriodStatus.SENT.name().equals(body.getStatus()) ? ReportingPeriodStatus.SENT : ReportingPeriodStatus.IN_PROGRESS;

        period.setStatus(status);
        customSurveyService.updateReportingPeriod(period);
        return period;
    }

    /**
     * Map the survey instance to a list of participant session answers.
     *
     * @param body the survey instance dto
     * @return the list of answers
     */
    private List<ParticipantSessionAnswerDTO> mapAnswers(CustomSurveyInstanceDTO body) {
        List<ParticipantSessionAnswerDTO> answers = new ArrayList<>();

        for (CustomSurveyQuestionGroupDTO customGroup : body.getPages()) {
            for (CustomQuestionDTO customQuestion : customGroup.getQuestions()) {

                ParticipantSessionQuestionDTO question = conversionService.convert(customQuestion, ParticipantSessionQuestionDTO.class);

                for (CustomQuestionAnswerDTO customQuestionAnswer : customQuestion.getAnswers()) {

                    CustomAnswerDTO customAnswer = customQuestionAnswer.getAnswer();

                    if (customAnswer != null) {

                        AnswerOptionDTO option = null;
                        if (customAnswer.getOption() != null) {
                            option = conversionService.convert(customAnswer.getOption(), AnswerOptionDTO.class);
                        }

                        ParticipantSessionQuestionAnswerDTO questionAnswer = ParticipantSessionQuestionAnswerDTO.builder().question(question)
                            .order(customQuestionAnswer.getOrder()).title(customQuestionAnswer.getTitle()).build();

                        ParticipantSessionAnswerDTO answer = ParticipantSessionAnswerDTO.builder().graphId(customAnswer.getGraphId())
                            .value(customAnswer.getValue()).option(option).type(customAnswer.getType()).question(questionAnswer).build();

                        answers.add(answer);
                    }

                }
            }
        }

        return answers;
    }
}
