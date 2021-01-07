/*
 * ReportingPeriodDTO.java - v1.0.0 - 17.04.2016
 * Copyright 2016, Reiner Hoppe. All rights reserved.
 */
package com.mainlevel.monitoring.survey.service.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Current view on a running survey.
 */
@Builder
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
@NoArgsConstructor
public class ResultSurvey {

    private Long reportingPeriodGid;

    private String unitName;

    private List<ResultQuestion> questions;

    private boolean readOnly;

    private SurveyLockInfo lockInfo;

}
