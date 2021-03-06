/*
 * Copyright (c) 2018 Mainlevel Consulting AG. All rights reserved.
 */
package com.mainlevel.monitoring.admin.api.dto.unit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Relation between units and other domain entities.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UnitLinkDTO {

    private String name;

    private String refId;

    private String type;

}
