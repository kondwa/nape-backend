/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.admin.api.dto.indicator;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO holding a list of indicator groups.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class IndicatorGroupListDTO {

    private List<IndicatorGroupDTO> groups;

}
