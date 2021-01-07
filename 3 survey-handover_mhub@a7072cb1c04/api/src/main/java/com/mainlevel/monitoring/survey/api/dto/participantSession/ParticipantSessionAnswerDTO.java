/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.api.dto.participantSession;

import com.mainlevel.monitoring.survey.api.dto.survey.answer.SurveyAnswerType;
import com.mainlevel.monitoring.survey.api.dto.survey.question.AnswerOptionDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO holding an answer of a participant session.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class ParticipantSessionAnswerDTO {

    private Long graphId;

    private SurveyAnswerType type;

    private AnswerOptionDTO option;

    private String value;

    private ParticipantSessionQuestionAnswerDTO question;

}
