/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */
package com.mainlevel.monitoring.business.custom.resource.dto.template;

import java.util.Date;
import java.util.List;

import com.mainlevel.monitoring.business.custom.resource.dto.CustomLanguageDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.unit.CustomUnitDTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO holding information about a template.
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class CustomTemplateDTO {

    @ApiModelProperty("Identifier of the template.")
    private String identifier;

    @ApiModelProperty("Name of the template.")
    private String name;

    @ApiModelProperty("Description of the template.")
    private String description;

    @ApiModelProperty("Version of the template.")
    private Long version;

    @ApiModelProperty("Creation date of the template.")
    private Date createdDate;

    @ApiModelProperty("Username of user who created the template.")
    private String createdBy;

    @ApiModelProperty("Name of user who created the template.")
    private String createdByName;

    @ApiModelProperty("Last modification date of the template.")
    private Date modifiedDate;

    @ApiModelProperty("Username of user who created the template.")
    private String modifiedBy;

    @ApiModelProperty("Name of user who created the template.")
    private String modifiedByName;

    @ApiModelProperty("Related organizational unit.")
    private CustomUnitDTO unit;

    @ApiModelProperty("Language of the template.")
    private CustomLanguageDTO language;

    @ApiModelProperty("Status of the Template.")
    private CustomTemplateStatus status;

    @ApiModelProperty("List of pages in the template.")
    private List<CustomTemplateQuestionGroupDTO> pages;

}
