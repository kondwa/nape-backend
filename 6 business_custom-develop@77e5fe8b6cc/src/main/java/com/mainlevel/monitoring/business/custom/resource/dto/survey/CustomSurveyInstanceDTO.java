/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.resource.dto.survey;

import java.util.Date;
import java.util.List;

import com.mainlevel.monitoring.business.custom.resource.dto.CustomLanguageDTO;
import com.mainlevel.monitoring.survey.api.dto.ClientType;
import com.mainlevel.monitoring.survey.api.dto.survey.SurveyType;
import com.mainlevel.monitoring.survey.api.dto.survey.SurveyVisibilityType;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO holding a single survey.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class CustomSurveyInstanceDTO {

    @ApiModelProperty(value = "Graph ID of the reporting period.")
    private Long instanceId;

    @ApiModelProperty(value = "Name of the survey.")
    private String surveyName;

    @ApiModelProperty(value = "HTML text for the survey start")
    private String introductionText;

    @ApiModelProperty(value = "HTML text for the survey end")
    private String closingText;

    @ApiModelProperty(value = "Type of survey")
    private SurveyType surveyType;

    @ApiModelProperty(value = "Visibility of survey")
    private SurveyVisibilityType surveyVisibilityType;

    @ApiModelProperty(value = "Reference ID of the template.")
    private String templateRefId;

    @ApiModelProperty(value = "Version of the template.")
    private Long templateVersion;

    @ApiModelProperty(value = "Title of the template.")
    private String templateTitle;

    @ApiModelProperty(value = "Name of the dedicated organizational unit.")
    private String unitName;

    @ApiModelProperty(value = "Start date of reporting period.")
    private Date start;

    @ApiModelProperty(value = "End date of reporting period.")
    private Date end;

    @ApiModelProperty(value = "Username who created the survey instance")
    private String author;

    @ApiModelProperty(value = "Username who is owner of this reporting period")
    private String owner;

    @ApiModelProperty(value = "Username who is original owner of this reporting period")
    private String originalOwner;

    @ApiModelProperty(value = "Date of creation.")
    private Date creationTime;

    @ApiModelProperty(value = "Date of last modification.")
    private Date lastEditTime;

    @ApiModelProperty(value = "Username (first+last) who made last activity")
    private String lastEditUser;

    @ApiModelProperty(value = "IP adress of the changing client.")
    private String lastEditAddress;

    @ApiModelProperty(value = "Type of the changing client.")
    private ClientType lastEditClientType;

    @ApiModelProperty(value = "Status of survey.")
    private String status;

    @ApiModelProperty(value = "Amount of answered questions in survey.")
    private Integer questionsAnswered;

    @ApiModelProperty(value = "Required amount of questions in survey.")
    private Integer questionsRequired;

    @ApiModelProperty(value = "Total amount of questions in survey.")
    private Integer questionsTotal;

    @ApiModelProperty("Language of the instance.")
    private CustomLanguageDTO language;

    @ApiModelProperty(value = "List of pages in the instance.")
    private List<CustomSurveyQuestionGroupDTO> pages;

}
