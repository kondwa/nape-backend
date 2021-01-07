/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.resource.dto.indicator;

import java.util.List;

import com.mainlevel.monitoring.admin.api.dto.filter.FilterDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.unit.CustomUnitDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO transporting indicator group information.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class CustomIndicatorGroupDTO {

    private String id;

    private String name;

    private CustomUnitDTO unit;

    private Integer size;

    private List<FilterDTO> filters;

}
