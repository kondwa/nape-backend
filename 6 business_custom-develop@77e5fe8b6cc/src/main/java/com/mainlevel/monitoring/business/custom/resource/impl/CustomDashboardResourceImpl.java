/*
 * Copyright (c) 2018 Mainlevel Consulting AG. All rights reserved.
 */
package com.mainlevel.monitoring.business.custom.resource.impl;

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
import com.mainlevel.monitoring.business.custom.resource.CustomDashboardResource;
import com.mainlevel.monitoring.business.custom.resource.dto.dashboard.CustomDashboardDTO;

/**
 * Default implementation of {@link CustomDashboardResource}.
 */
@RestController
@Secured(ROLE_USER)
public class CustomDashboardResourceImpl implements CustomDashboardResource {

    @Autowired
    private DashboardResource dashboardResource;

    @Autowired
    private ConversionService conversionService;

    @Override
    public ResponseEntity<CustomDashboardDTO> getDashboard(@PathVariable("key") String key) {

        ResponseEntity<DashboardDTO> response = dashboardResource.getDashboard(key);

        CustomDashboardDTO dashboard = conversionService.convert(response.getBody(), CustomDashboardDTO.class);

        return ResponseEntity.ok(dashboard);
    }

    @Override
    public ResponseEntity<Map<String, Object>> updateDashboardItemProperties(@PathVariable("key") String key, @PathVariable("itemKey") String itemKey,
        @RequestBody Map<String, Object> properties) {

        ResponseEntity<Map<String, Object>> response = dashboardResource.updateDashboardItem(key, itemKey, properties);
        return ResponseEntity.ok(response.getBody());
    }
}
