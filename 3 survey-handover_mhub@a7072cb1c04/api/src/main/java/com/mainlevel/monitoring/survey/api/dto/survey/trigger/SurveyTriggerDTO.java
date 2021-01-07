/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */
package com.mainlevel.monitoring.survey.api.dto.survey.trigger;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO for a question trigger.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class SurveyTriggerDTO {

    @ApiModelProperty("Graph ID of the trigger.")
    private Long graphId;

    @ApiModelProperty("Order index of the trigger.")
    private Integer index;

    @ApiModelProperty("Appropriate option index that triggers this trigger.")
    private Integer optionIndex;

    @ApiModelProperty("Appropriate option name that triggers this trigger.")
    private String optionValue;

    @ApiModelProperty("Index of the referenced target question.")
    private Integer targetQuestionIndex;

    @ApiModelProperty("Name of the referenced target question.")
    private String targetQuestionName;

    @ApiModelProperty("Type of trigger action.")
    private SurveyTriggerType actionType;

    @ApiModelProperty("Type of trigger action.")
    private SurveyTriggerCompareType compareType;

}
