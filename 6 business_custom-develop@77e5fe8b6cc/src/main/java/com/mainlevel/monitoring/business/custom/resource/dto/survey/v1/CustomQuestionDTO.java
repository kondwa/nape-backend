/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.resource.dto.survey.v1;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO representing a question in a survey result holding a list of anwsers and rows.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
@Deprecated
public class CustomQuestionDTO {

    @ApiModelProperty(value = "Name of the question.")
    private String name;

    @ApiModelProperty(value = "The list of anwsers for this question.")
    private List<CustomAnswerDTO> answers;

    @ApiModelProperty(value = "The list of rows for this question in case of a.")
    private List<CustomRowDTO> rows;

}
