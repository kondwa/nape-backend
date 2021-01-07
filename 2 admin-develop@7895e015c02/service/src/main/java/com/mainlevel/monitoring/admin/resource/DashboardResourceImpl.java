/*
 * Copyright (c) 2018 Mainlevel Consulting AG. All rights reserved.
 */
package com.mainlevel.monitoring.admin.resource;

import static com.mainlevel.monitoring.common.constant.RoleConstant.ROLE_USER;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mainlevel.monitoring.admin.api.dto.dashboard.DashboardDTO;
import com.mainlevel.monitoring.admin.api.resource.DashboardResource;
import com.mainlevel.monitoring.admin.repository.entity.DashboardEntity;
import com.mainlevel.monitoring.admin.repository.entity.DashboardItemEntity;
import com.mainlevel.monitoring.admin.service.DashboardService;

/**
 * Default implementation of {@link DashboardResource}.
 */
@RestController
@Secured(ROLE_USER)
public class DashboardResourceImpl implements DashboardResource {

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private ConversionService conversionService;

    @Override
    public ResponseEntity<DashboardDTO> getDashboard(@PathVariable("key") String key) {

        DashboardEntity dashboardEntity = dashboardService.findByKey(key);

        DashboardDTO dashboard = conversionService.convert(dashboardEntity, DashboardDTO.class);

        return ResponseEntity.ok(dashboard);
    }

    @Override
    public ResponseEntity<Map<String, Object>> updateDashboardItem(@PathVariable("key") String key, @PathVariable("itemKey") String itemKey,
        @RequestBody Map<String, Object> properties) {

        DashboardEntity dashboard = dashboardService.findByKey(key);
        DashboardItemEntity item = dashboard.getItems().stream().filter(i -> i.getKey().equals(itemKey)).findFirst().get();

        item.getProperties().clear();
        item.getProperties().putAll(properties);

        dashboardService.save(dashboard);

        return ResponseEntity.ok(properties);
    }
}
