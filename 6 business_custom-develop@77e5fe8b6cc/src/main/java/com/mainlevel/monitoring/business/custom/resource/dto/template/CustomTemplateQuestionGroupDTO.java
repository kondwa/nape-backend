/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.resource.dto.template;

import java.util.List;

import com.mainlevel.monitoring.business.custom.resource.dto.question.CustomQuestionDTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO holding infos and questions of a single template page.
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class CustomTemplateQuestionGroupDTO {

    @ApiModelProperty("Name of the template page.")
    private String name;

    @ApiModelProperty("Description of the template page")
    private String description;

    @ApiModelProperty("Flag that determinates whether the page is visible or not.")
    private Boolean visible;

    @ApiModelProperty("Questions of the template page")
    private List<CustomQuestionDTO> questions;

}
