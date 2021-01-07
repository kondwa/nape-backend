/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.admin.api.dto.indicator;

import java.util.List;

import com.mainlevel.monitoring.admin.api.dto.filter.FilterDTO;
import com.mainlevel.monitoring.admin.api.dto.unit.UnitDTO;

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
public class IndicatorGroupDTO {

    private String id;

    private String name;

    private UnitDTO unit;

    private Integer size;

    private List<FilterDTO> filters;

}
