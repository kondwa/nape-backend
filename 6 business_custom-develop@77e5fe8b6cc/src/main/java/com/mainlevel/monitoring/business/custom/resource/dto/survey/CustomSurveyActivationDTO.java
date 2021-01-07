/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.resource.dto.survey;

import com.mainlevel.monitoring.survey.api.dto.survey.SurveyType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO holding parameter for activating a survey.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class CustomSurveyActivationDTO {

    private String surveyName;

    private String templateIdentifier;

    private Integer templateVersion;

    private SurveyType type;

    private Boolean anonymous;

    private Integer targetInstances;

    private String unitForeignId;

}
