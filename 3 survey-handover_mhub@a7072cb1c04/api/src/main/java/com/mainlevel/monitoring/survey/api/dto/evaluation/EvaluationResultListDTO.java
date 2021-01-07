/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.api.dto.evaluation;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * List of evaluation result entries.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class EvaluationResultListDTO {

    @ApiModelProperty("The evaluation title.")
    private String title;

    @ApiModelProperty("Chart type of the evaluation.")
    private EvaluationChartType chartType;

    @ApiModelProperty("Description of the X-Axis.")
    private String xAxisDesc;

    @ApiModelProperty("Description of the Y-Axis.")
    private String yAxisDesc;

    @ApiModelProperty(value = "The list of results")
    private List<EvaluationResultEntryDTO> data;

}
