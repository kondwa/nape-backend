/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.api.dto.evaluation;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * A single evaluation result dto.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class EvaluationResultEntryDTO {

    @ApiModelProperty("Name of the record.")
    private String name;

    @ApiModelProperty("Value of the X-Axis.")
    private String xAxisValue;

    @ApiModelProperty("Value of the Y-Axis.")
    private Number yAxisValue;

}
