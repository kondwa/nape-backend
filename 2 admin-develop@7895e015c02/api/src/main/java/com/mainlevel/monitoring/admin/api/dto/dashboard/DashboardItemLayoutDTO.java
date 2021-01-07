/*
 * Copyright (c) 2018 Mainlevel Consulting AG. All rights reserved.
 */
package com.mainlevel.monitoring.admin.api.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO holding infos about the widget layout.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class DashboardItemLayoutDTO {

    private String height;

    private Integer xs;

    private Integer sm;

    private Integer md;

    private Integer lg;

    private Integer xl;

}
