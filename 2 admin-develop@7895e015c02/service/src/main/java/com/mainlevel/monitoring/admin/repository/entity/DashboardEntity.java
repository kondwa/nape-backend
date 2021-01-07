/*
 * Copyright (c) 2018 Mainlevel Consulting AG. All rights reserved.
 */
package com.mainlevel.monitoring.admin.repository.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mainlevel.monitoring.admin.api.dto.dashboard.DashboardType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Entity for storing dashboard layout.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
@Document(collection = "dashboard")
public class DashboardEntity {

    @Id
    private String id;

    @Indexed(unique = true)
    private String key;

    private String label;

    private DashboardType type;

    private Integer spacing;

    private String direction;

    private List<DashboardItemEntity> items;
}
