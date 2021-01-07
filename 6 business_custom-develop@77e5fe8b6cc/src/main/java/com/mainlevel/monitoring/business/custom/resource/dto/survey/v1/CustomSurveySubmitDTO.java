/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.resource.dto.survey.v1;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO transfering the base attributes for survey DTOs.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
@Deprecated
public class CustomSurveySubmitDTO {

    @NotNull
    @ApiModelProperty(value = "ID of the template.", required = true)
    private String templateId;

    @NotNull
    @ApiModelProperty(value = "Version of the template.", required = true)
    private long templateVersion;

    @ApiModelProperty(value = "ID of the organizational unit.", required = false)
    private long unitId;
    
    @ApiModelProperty(value = "Timestamp of Submission", required = false)
    private String submissionTimestamp;
    
    @ApiModelProperty(value = "ID of the survey in foreign system.", required = false)
    private String foreignSurveyId;
    
    @ApiModelProperty(value = "Version of the app submitting data", required = false)
    private String appVersion;

    @ApiModelProperty(value = "List of survey questions.")
    private List<CustomQuestionDTO> questions;
}
