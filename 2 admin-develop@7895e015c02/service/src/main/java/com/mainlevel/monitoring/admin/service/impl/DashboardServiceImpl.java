/*
 * Copyright (c) 2018 Mainlevel Consulting AG. All rights reserved.
 */
package com.mainlevel.monitoring.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mainlevel.monitoring.admin.repository.DashboardRepository;
import com.mainlevel.monitoring.admin.repository.entity.DashboardEntity;
import com.mainlevel.monitoring.admin.service.DashboardService;

/**
 * Default implementation of {@link DashboardService}.
 */
@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private DashboardRepository dashboardRepository;

    @Override
    public DashboardEntity findById(String id) {
        return dashboardRepository.findOne(id);
    }

    @Override
    public DashboardEntity findByKey(String key) {
        return dashboardRepository.findByKey(key);
    }

    @Override
    public DashboardEntity save(DashboardEntity dashboard) {
        return dashboardRepository.save(dashboard);
    }

}
