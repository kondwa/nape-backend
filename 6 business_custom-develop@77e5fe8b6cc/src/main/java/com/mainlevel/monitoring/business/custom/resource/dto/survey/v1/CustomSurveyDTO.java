/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.resource.dto.survey.v1;

import java.util.Date;

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
@Deprecated
public class CustomSurveyDTO {

    @ApiModelProperty(value = "ID of the template.")
    private String templateId;

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

    @ApiModelProperty(value = "Date of last modification.")
    private Date lastEditTime;

    @ApiModelProperty(value = "Username (first+last) who made last activity")
    private String lastEditUser;

    @ApiModelProperty(value = "Status of survey.")
    private String status;

}
