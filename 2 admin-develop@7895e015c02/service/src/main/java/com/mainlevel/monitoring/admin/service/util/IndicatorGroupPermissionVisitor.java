/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.admin.service.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mainlevel.monitoring.admin.repository.entity.IndicatorGroupEntity;
import com.mainlevel.monitoring.admin.repository.entity.UnitEntity;
import com.mainlevel.monitoring.admin.repository.entity.UnitVisitor;

/**
 * Visitor for checking a template against the organizational structure.
 */
public class IndicatorGroupPermissionVisitor implements UnitVisitor {

    private List<IndicatorGroupEntity> allGroups;

    private final Set<IndicatorGroupEntity> permittedGroups;

    /**
     * Constructor for IndicatorGroupPermissionVisitor.
     *
     * @param groups the list of indicator groups to check
     */
    public IndicatorGroupPermissionVisitor(List<IndicatorGroupEntity> groups) {
        this.allGroups = groups;
        this.permittedGroups = new HashSet<>();
    }

    @Override
    public void visit(UnitEntity unit) {

        allGroups.forEach(g -> {
            if (g.getUnit() == null || unit.equals(g.getUnit())) {
                permittedGroups.add(g);
            }
        });

        allGroups.removeAll(permittedGroups);
    }

    /**
     * Retrieves the list of permitted indicator groups.
     *
     * @return the permitted groups
     */
    public Set<IndicatorGroupEntity> getPermittedGroups() {
        return permittedGroups;
    }
}
