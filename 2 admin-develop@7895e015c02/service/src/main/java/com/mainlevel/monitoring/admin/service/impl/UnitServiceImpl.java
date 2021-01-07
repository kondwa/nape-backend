/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.admin.service.impl;

import static org.springframework.util.StringUtils.isEmpty;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mainlevel.monitoring.admin.repository.UnitRepository;
import com.mainlevel.monitoring.admin.repository.entity.DashboardEntity;
import com.mainlevel.monitoring.admin.repository.entity.UnitEntity;
import com.mainlevel.monitoring.admin.service.DashboardService;
import com.mainlevel.monitoring.admin.service.UnitService;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of {@link UnitService}
 */
@Slf4j
@Service
public class UnitServiceImpl implements UnitService {

    private static final String DEFAULT_DASHBOARD_TYPE = "DEFAULT_PROJECT";

    private static final String DEFAULT_DASHBOARD_ID = "1";

    private static final String DEFAULT_DASHBOARD_SUFFIX = " Dashboard";

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private DashboardService dashboardService;

    @Override
    public UnitEntity findUnitById(String unitId) {
        return unitRepository.findOne(unitId);
    }

    @Override
    public UnitEntity findUnitByForeignId(String foreignId) {
        return unitRepository.findByForeignId(foreignId);
    }

    @Override
    public List<UnitEntity> findUnitsByType(String type) {
        return unitRepository.findByType(type);
    }

    @Override
    public UnitEntity saveByForeignId(UnitEntity unit) {
        UnitEntity db = null;
        if ((unit != null) && !isEmpty(unit.getForeignId())) {
            db = unitRepository.findByForeignId(unit.getForeignId());
            if (db == null) {
                db = UnitEntity.builder().foreignId(unit.getForeignId()).build();
            }

            if (unit.getDashboards() == null && unit.getType().equals("Project")) {
                DashboardEntity dashboard = createDefaultDashboard(unit);
                db.setDashboards(Arrays.asList(dashboard.getKey()));
            } else {
                db.setDashboards(unit.getDashboards());
            }

            db.setName(unit.getName());
            db.setType(unit.getType());
            db.setParent(unit.getParent());
            db = unitRepository.save(db);
        }

        if (log.isDebugEnabled() && (db != null)) {
            log.debug("Saved generic unit: {} {} {}", db.getId(), db.getName(), db.getType());
        }

        return db;
    }

    /**
     * Creates and persists a default project dashboard for the given unit.
     *
     * @param project the project to create the dashboard for
     * @return the saved dashboard
     */
    private DashboardEntity createDefaultDashboard(UnitEntity project) {
        String dashboardKey = project.getForeignId() + DEFAULT_DASHBOARD_ID;
        String dashboardName = project.getName() + DEFAULT_DASHBOARD_SUFFIX;

        DashboardEntity dashboard = dashboardService.findByKey(DEFAULT_DASHBOARD_TYPE);
        dashboard.setId(null);
        dashboard.setKey(dashboardKey);
        dashboard.setLabel(dashboardName);
        dashboardService.save(dashboard);
        return dashboard;
    }

    @Override
    public UnitEntity findDeepForUser(String username) {

        // TODO Intelligent Loader
        //
        // final Map<String, UnitEntity> units = new HashMap<>();
        //
        // UserEntity user = userService.findByUserId(userid);
        //
        // List<UserParticipationEntity> participations = user.getParticipations();
        //
        // for (UserParticipationEntity participation : participations) {
        // UnitEntity unit = participation.getUnit();
        //
        // loadDeep(unit);
        //
        // if (!unit.isRoot()) {
        // List<UnitEntity> children = unit.getParent().getChildren();
        // if (children == null) {
        // children = new ArrayList<>();
        // unit.getParent().setChildren(children);
        // }
        // children.add(unit);
        // }
        // }

        UnitEntity root = unitRepository.findByRootTrue();

        loadDeep(root);

        return root;
    }

    /**
     * Load the unit including all subunits.
     *
     * @param unit the parent unit
     */
    @Override
    public UnitEntity loadDeep(UnitEntity unit) {
        List<UnitEntity> children = unitRepository.findByParentId(unit.getId());

        if (children != null) {
            children.forEach(child -> loadDeep(child));
            unit.setChildren(children);
        }

        return unit;
    }

    @Override
    public UnitEntity deleteByForeignId(String foreignId) {

        UnitEntity unit = unitRepository.findByForeignId(foreignId);
        unitRepository.delete(unit);

        return unit;
    }

}
