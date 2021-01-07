/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.api.dto;

import com.mainlevel.monitoring.survey.api.dto.survey.ReportingPeriodStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO holding report data.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class SurveySummaryResultDTO {

    private String unitName;

    private String surveyName;

    private String creator;

    private Long reportingPeriodId;

    private ReportingPeriodStatus status;

    private String questionGroup;

    private Long questionGroupId;

    private Integer questionIndex;

    private String questionTitle;

    private Long questionId;

    private QuestionType questionType;

    private String answer;

}
