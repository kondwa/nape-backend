/*
 * Copyright (c) 2018 Mainlevel Consulting AG. All rights reserved.
 */
package com.mainlevel.monitoring.business.custom.resource.dto.dashboard;

import java.util.List;

import com.mainlevel.monitoring.admin.api.dto.dashboard.DashboardType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO holding information about a dashboard.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class CustomDashboardDTO {

    private String id;

    private String key;

    private String label;

    private DashboardType type;

    private Integer spacing;

    private String direction;

    private List<CustomDashboardItemDTO> items;

}
