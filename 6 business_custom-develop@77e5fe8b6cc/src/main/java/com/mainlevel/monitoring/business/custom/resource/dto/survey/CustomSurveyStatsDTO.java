/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */
package com.mainlevel.monitoring.business.custom.resource.dto.survey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO holding statistics about a survey.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class CustomSurveyStatsDTO {

    private Long graphId;

    private String surveyName;

    private String templateRefId;

    private String templateName;

    private Long templateVersion;

    private String unit;

    private String createdBy;

    private Long creationDate;

    private String type;

    private String visibility;

    private Integer questionGroups;

    private Integer questions;

    private Integer targetInstances;

    private float targetPercentage;

    private int instances;

    private int newInstances;

    private float newPercentage;

    private int runningInstances;

    private float runningPercentage;

    private int submittedInstances;

    private float submittedPercentage;

}
