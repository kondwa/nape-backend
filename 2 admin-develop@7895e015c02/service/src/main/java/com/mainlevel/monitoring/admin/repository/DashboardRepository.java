/*
 * Copyright (c) 2018 Mainlevel Consulting AG. All rights reserved.
 */
package com.mainlevel.monitoring.admin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mainlevel.monitoring.admin.repository.entity.DashboardEntity;

/**
 * Repository for maintaining dashboards.
 */
public interface DashboardRepository extends MongoRepository<DashboardEntity, String> {

    /**
     * Find the given dashboard entity by its key.
     *
     * @param key the dashboard key
     * @return the dashboard entity
     */
    DashboardEntity findByKey(String key);
}
