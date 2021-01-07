/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.admin.api.dto.theme;

import java.util.Map;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO transfering theme information.
 */
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ThemeDTO {

    @ApiModelProperty("ID of the theme")
    private String identifier;

    @ApiModelProperty("Name of the theme")
    private String name;

    @ApiModelProperty("Title of the application")
    private String applicationTitle;

    @ApiModelProperty("Base64 encoded logo image")
    private String logo;

    @ApiModelProperty("Base64 encoded logo image (secondary)")
    private String secondaryLogo;

    @ApiModelProperty("Application Styles")
    private Map<String, Object> styles;
}
