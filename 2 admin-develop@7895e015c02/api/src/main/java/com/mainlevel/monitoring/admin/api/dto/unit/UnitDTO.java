/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.admin.api.dto.unit;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO holding the organizational unit structure.
 */
@Builder
@Data
@EqualsAndHashCode(callSuper = false, exclude = {"children"})
@ToString(callSuper = false, exclude = {"children"})
@AllArgsConstructor
@NoArgsConstructor
public class UnitDTO {

    private String id;

    private String foreignId;

    private String name;

    private String type;

    private String parentId;

    private List<String> dashboards;

    private List<UnitDTO> children;

    private List<UnitLinkDTO> links;
}
