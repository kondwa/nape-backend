/*
 * Copyright (c) 2018 Mainlevel Consulting AG. All rights reserved.
 */
package com.mainlevel.monitoring.admin.service;

import com.mainlevel.monitoring.admin.repository.entity.DashboardEntity;

/**
 * Service for maintaining dashboards.
 */
public interface DashboardService {

    /**
     * Find the dashboard with the given id.
     *
     * @param id dashbaord id
     * @return dashboard entity
     */
    DashboardEntity findById(String id);

    /**
     * Find the dashboard with the given key.
     *
     * @param key dashbaord id
     * @return dashboard entity
     */
    DashboardEntity findByKey(String key);

    /**
     * Save the given dashboard to database.
     *
     * @param dashboard the dashboard to save
     * @return the saved dashbaord
     */
    DashboardEntity save(DashboardEntity dashboard);

}
