/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.resource.dto.answer;

import com.mainlevel.monitoring.business.custom.resource.dto.question.CustomOptionDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.answer.SurveyAnswerType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO holding information about a question answer.
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class CustomAnswerDTO {

    private Long graphId;

    private SurveyAnswerType type;

    private CustomOptionDTO option;

    private String value;

}
