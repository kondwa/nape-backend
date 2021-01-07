/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */
package com.mainlevel.monitoring.survey.api.dto.survey;

import java.util.Date;

import com.mainlevel.monitoring.survey.api.dto.ClientType;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO holding reporting period information.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class ReportingPeriodOverviewDTO {

    @ApiModelProperty(value = "Graph ID of the reporting period.")
    private Long reportPeriodGid;

    @ApiModelProperty(value = "Reference ID of the related survey template.")
    private String templateId;

    @ApiModelProperty(value = "Title of the survey.")
    private String templateTitle;

    @ApiModelProperty(value = "Version of the survey template.")
    private Long templateVersion;

    @ApiModelProperty(value = "Name of the dedicated org unit.")
    private String unitName;

    @ApiModelProperty(value = "Start date of reporting period.")
    private Date start;

    @ApiModelProperty(value = "End date of reporting period.")
    private Date end;

    @ApiModelProperty(value = "Username who created reporting period")
    private String author;

    @ApiModelProperty(value = "Username who is owner of this reporting period")
    private String owner;

    @ApiModelProperty(value = "Username who is original owner of this reporting period")
    private String originalOwner;

    @ApiModelProperty(value = "Date of creation.")
    private Date creationTime;

    @ApiModelProperty(value = "Date of last modification.")
    private Date lastEditTime;

    @ApiModelProperty(value = "Username who made last activity")
    private String lastEditUser;

    @ApiModelProperty(value = "IP adress of the changing client.")
    private String lastEditAddress;

    @ApiModelProperty(value = "Type of the changing client.")
    private ClientType lastEditClientType;

    @ApiModelProperty(value = "Status of the reporting period.")
    private String status;

    @ApiModelProperty(value = "Amount of answered questions in reporting period.")
    private Integer questionsAnswered;

    @ApiModelProperty(value = "Required amount of questions in reporting period.")
    private Integer questionsRequired;

    @ApiModelProperty(value = "Total amount of questions in reporting period.")
    private Integer questionsTotal;

}
