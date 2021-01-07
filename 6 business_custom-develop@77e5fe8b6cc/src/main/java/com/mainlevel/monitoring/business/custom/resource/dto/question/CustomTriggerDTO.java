/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.resource.dto.question;

import com.mainlevel.monitoring.survey.api.dto.survey.trigger.SurveyTriggerCompareType;
import com.mainlevel.monitoring.survey.api.dto.survey.trigger.SurveyTriggerType;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO holding information about a question trigger.
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class CustomTriggerDTO {

    @ApiModelProperty("Graph ID of the trigger.")
    private Long graphId;

    @ApiModelProperty("Order index of the trigger.")
    private Integer index;

    @ApiModelProperty("Appropriate answer index that triggers this trigger.")
    private Integer optionIndex;

    @ApiModelProperty("Index of the referenced target question.")
    private Integer targetQuestionIndex;

    @ApiModelProperty("Type of trigger action.")
    private SurveyTriggerType actionType;

    @ApiModelProperty("Type of trigger action.")
    private SurveyTriggerCompareType compareType;

}
