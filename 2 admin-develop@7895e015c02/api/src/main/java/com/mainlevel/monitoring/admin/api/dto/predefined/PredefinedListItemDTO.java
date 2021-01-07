/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.admin.api.dto.predefined;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO representing a single entry in a predefined list.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class PredefinedListItemDTO {

    @ApiModelProperty("ID of the list item.")
    private String id;

    @ApiModelProperty("Code of the list item.")
    private String code;

    @ApiModelProperty("Category of the list item.")
    private String category;

    @ApiModelProperty("Type of the list item.")
    private String type;
}
