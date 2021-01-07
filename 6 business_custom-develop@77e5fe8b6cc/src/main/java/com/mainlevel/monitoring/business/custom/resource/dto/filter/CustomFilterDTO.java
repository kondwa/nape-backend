/*
 * Copyright (c) 2018 Mainlevel Consulting AG. All rights reserved.
 */
package com.mainlevel.monitoring.business.custom.resource.dto.filter;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO holding a single filter.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class CustomFilterDTO {

    private String name;

    private String label;

    private String description;

    private String type;

    private List<CustomFilterOptionDTO> options;

}
