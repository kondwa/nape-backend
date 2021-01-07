/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.api.dto;

import java.util.Date;

import com.mainlevel.monitoring.survey.api.dto.survey.ReportingPeriodDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.SurveyDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.SurveyVisibilityType;
import com.mainlevel.monitoring.survey.api.dto.user.SurveyUserDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO holding information about a link.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class SurveyLinkDTO {

    private Long graphId;

    private String suffix;

    private String token;

    private boolean active;

    private Date expiry;

    private String owner;

    private SurveyVisibilityType visibility;

    private SurveyUserDTO user;

    private SurveyDTO survey;

    private ReportingPeriodDTO reportingPeriod;

}
