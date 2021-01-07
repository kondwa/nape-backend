/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.resource.dto.question;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO holding a row description.
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class CustomMatrixRowDTO {

    @ApiModelProperty("ID of the row.")
    private Long graphId;

    @ApiModelProperty("Name of the row.")
    private String name;

    @ApiModelProperty("Description of the row.")
    private String description;

    @ApiModelProperty("Index of the row.")
    private Integer index;

}
