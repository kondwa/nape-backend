/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.resource.dto.survey;

import java.util.Date;
import java.util.List;

import com.mainlevel.monitoring.survey.api.dto.survey.SurveyStatus;
import com.mainlevel.monitoring.survey.api.dto.survey.SurveyType;
import com.mainlevel.monitoring.survey.api.dto.survey.SurveyVisibilityType;

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
public class CustomSurveyDTO {

    private Long graphId;

    private String surveyName;

    private String templateRefId;

    private Long templateVersion;

    private String templateTitle;

    private Date creationTime;

    private SurveyType type;

    private SurveyStatus status;

    private SurveyVisibilityType visibility;

    private String unitName;

    private Integer targetInstances;

    private String link;

    private List<CustomSurveyQuestionGroupDTO> questionGroups;

}
