/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.api.dto.survey.answer;

import com.mainlevel.monitoring.survey.api.dto.survey.question.AnswerOptionDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO holding a single answer. Depending on the question, the answer may have a value (text, number), an option (checkbox, radio, ...) or both (radio
 * with other option).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class SurveyAnswerDTO {

    private Long graphId;

    private SurveyAnswerType type;

    private AnswerOptionDTO option;

    private String value;

}
