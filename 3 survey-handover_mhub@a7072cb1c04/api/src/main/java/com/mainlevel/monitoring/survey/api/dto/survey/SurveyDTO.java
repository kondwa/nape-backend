/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.api.dto.survey;

import java.util.Date;
import java.util.List;

import com.mainlevel.monitoring.survey.api.dto.OrganizationalUnitDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.question.SurveyQuestionGroupDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO holding the reporting period.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class SurveyDTO {

    private Long graphId;

    private String surveyName;

    private String templateRefId;

    private Long templateVersion;

    private String templateTitle;

    private String introductionText;

    private String closingText;

    private Date creationTime;

    private SurveyType type;

    private SurveyStatus status;

    private SurveyVisibilityType visibility;

    private OrganizationalUnitDTO unit;

    private Integer targetInstances;

    private List<SurveyQuestionGroupDTO> questionGroups;

}
