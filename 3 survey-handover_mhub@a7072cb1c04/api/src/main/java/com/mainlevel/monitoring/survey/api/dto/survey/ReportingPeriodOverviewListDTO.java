/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.api.dto.survey;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO holding a list or reporting period overviews.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class ReportingPeriodOverviewListDTO {

    private List<ReportingPeriodOverviewDTO> overviews;

}
