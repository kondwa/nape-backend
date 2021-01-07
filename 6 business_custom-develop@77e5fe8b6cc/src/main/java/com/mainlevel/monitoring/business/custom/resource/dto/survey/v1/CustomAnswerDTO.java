/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.resource.dto.survey.v1;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO for transfering a single anwser value and metadata.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
@Deprecated
public class CustomAnswerDTO {

    @ApiModelProperty(value = "Name of the answer.")
    private String name;

    @ApiModelProperty(value = "Label of the answer.")
    private String label;

    @ApiModelProperty(value = "Label of the column.")
    private String colLabel;

    @ApiModelProperty(value = "Value of the anwser.")
    private String value;

    @ApiModelProperty(value = "Text of the anwser.")
    private String text;

}
