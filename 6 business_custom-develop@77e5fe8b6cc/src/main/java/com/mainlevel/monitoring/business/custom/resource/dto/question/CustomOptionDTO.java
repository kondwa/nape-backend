/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.resource.dto.question;

import com.mainlevel.monitoring.survey.api.dto.survey.question.AnswerOptionType;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO holding information about a single option of a choice question.
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class CustomOptionDTO {

    @ApiModelProperty("ID of the option.")
    private Long graphId;

    @ApiModelProperty("Name of the option.")
    private String name;

    @ApiModelProperty("Index of the option.")
    private Integer index;

    @ApiModelProperty("Description of the option.")
    private String description;

    @ApiModelProperty("Type of the option.")
    private AnswerOptionType type;

    @ApiModelProperty("Value of the option")
    private Object value;

}
