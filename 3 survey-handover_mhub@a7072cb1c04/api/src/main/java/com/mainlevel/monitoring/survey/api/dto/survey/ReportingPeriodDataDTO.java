/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.api.dto.survey;

import java.util.Date;
import java.util.List;

import com.mainlevel.monitoring.survey.api.dto.survey.question.SurveyQuestionGroupDTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO holding all infos including answers of a reporting period.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class ReportingPeriodDataDTO {

    @ApiModelProperty(value = "Graph ID of the reporting period.")
    private Long reportPeriodGid;

    @ApiModelProperty(value = "Title of the survey.")
    private String surveyName;

    @ApiModelProperty(value = "HTML text for the survey start")
    private String introductionText;

    @ApiModelProperty(value = "HTML text for the survey end")
    private String closingText;

    @ApiModelProperty(value = "Type of survey")
    private SurveyType surveyType;

    @ApiModelProperty(value = "Visibility of survey")
    private SurveyVisibilityType surveyVisibilityType;

    @ApiModelProperty(value = "Reference ID of the related survey template.")
    private String templateId;

    @ApiModelProperty(value = "Title of the template.")
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

    @ApiModelProperty(value = "Date of creation.")
    private Date creationTime;

    @ApiModelProperty(value = "Date of last modification.")
    private Date lastEditTime;

    @ApiModelProperty(value = "Username who made last activity")
    private String lastEditUser;

    @ApiModelProperty(value = "Status of the reporting period.")
    private ReportingPeriodStatus status;

    @ApiModelProperty(value = "List of question groups.")
    private List<SurveyQuestionGroupDTO> questionGroups;

}
