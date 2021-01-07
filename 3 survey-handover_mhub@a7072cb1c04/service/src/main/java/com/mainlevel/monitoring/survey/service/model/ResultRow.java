/*
 * ReportingPeriodDTO.java - v1.0.0 - 17.04.2016
 * Copyright 2016, Reiner Hoppe. All rights reserved.
 */
package com.mainlevel.monitoring.survey.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Row of table question.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class ResultRow {

    private String name;

}
