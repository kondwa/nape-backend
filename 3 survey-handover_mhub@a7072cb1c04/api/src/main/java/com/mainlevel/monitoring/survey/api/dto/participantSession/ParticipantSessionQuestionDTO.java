/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.api.dto.participantSession;

import java.util.List;

import com.mainlevel.monitoring.survey.api.dto.QuestionType;
import com.mainlevel.monitoring.survey.api.dto.survey.question.AnswerOptionDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO for questions refered from a participant session answer.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class ParticipantSessionQuestionDTO {

    private Long graphId;

    private String name;

    private Integer index;

    private String title;

    private String description;

    private Boolean mandatory;

    private Boolean visible;

    private QuestionType type;

    private List<AnswerOptionDTO> options;

}
