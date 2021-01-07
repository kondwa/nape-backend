/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.resource.dto.survey;

import java.util.List;

import com.mainlevel.monitoring.business.custom.resource.dto.question.CustomQuestionDTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO holding infos and questions of a single survey question group.
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class CustomSurveyQuestionGroupDTO {

    @ApiModelProperty("ID of the question group.")
    private Long graphId;

    @ApiModelProperty("Name of the question group.")
    private String name;

    @ApiModelProperty("Description of the question group")
    private String description;

    @ApiModelProperty("Flag that determinates whether the group is visible or not.")
    private Boolean visible;

    @ApiModelProperty(" Index of this question group.")
    private Integer index;

    @ApiModelProperty("Questions of the  question group")
    private List<CustomQuestionDTO> questions;

}
