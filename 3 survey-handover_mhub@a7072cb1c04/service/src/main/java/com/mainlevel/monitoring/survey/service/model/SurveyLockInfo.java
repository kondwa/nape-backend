/*
 * ReportingPeriodDTO.java - v1.0.0 - 17.04.2016
 * Copyright 2016, Reiner Hoppe. All rights reserved.
 */
package com.mainlevel.monitoring.survey.service.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Lock information of locked reporting period.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class SurveyLockInfo {

    private Boolean locked;

    private Boolean readOnly;

    private Date lockFrom;

    private String lockByUser;

}
