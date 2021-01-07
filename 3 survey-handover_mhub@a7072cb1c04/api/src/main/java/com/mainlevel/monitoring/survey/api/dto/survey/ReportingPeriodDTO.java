/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.api.dto.survey;

import java.util.Date;

import com.mainlevel.monitoring.survey.api.dto.ClientDTO;
import com.mainlevel.monitoring.survey.api.dto.OrganizationalUnitDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO holding the reporting period.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class ReportingPeriodDTO {

    private Long graphId;

    private Date start;

    private Date end;

    private Date created;
    
    private String submissionTimestamp;
    
    private String foreignSurveyId;

    private ReportingPeriodStatus status;

    private OrganizationalUnitDTO unit;

    private SurveyDTO survey;

    private ClientDTO client;

}
