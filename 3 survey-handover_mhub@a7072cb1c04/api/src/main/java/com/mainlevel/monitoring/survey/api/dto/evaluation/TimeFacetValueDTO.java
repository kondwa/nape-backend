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
 * DTO holding a time facet with time ranges and label.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class TimeFacetValueDTO {

    @ApiModelProperty(value = "Starting time of this time facet.")
    private Long startTime;

    @ApiModelProperty(value = "End time of this time facet.")
    private Long endTime;

    @ApiModelProperty(value = "Label of this time facet.")
    private String label;

}
