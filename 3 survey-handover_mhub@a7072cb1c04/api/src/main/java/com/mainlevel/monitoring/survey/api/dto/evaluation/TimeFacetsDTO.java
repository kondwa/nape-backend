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
 * DTO holding a list of time facet values for an interval.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class TimeFacetsDTO {

    @ApiModelProperty(value = "Type of interval.")
    private TimeFacetIntervalType intervalType;

    @ApiModelProperty("List of facets for the given interval.")
    private List<TimeFacetValueDTO> facets;

}
