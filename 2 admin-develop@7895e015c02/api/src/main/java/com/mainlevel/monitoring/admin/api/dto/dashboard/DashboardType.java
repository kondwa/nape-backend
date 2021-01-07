/*
 * Copyright (c) 2018 Mainlevel Consulting AG. All rights reserved.
 */
package com.mainlevel.monitoring.admin.api.dto.dashboard;

/**
 * Type of dashboard.
 */
public enum DashboardType {

    /**
     * Default dashboard after login.
     */
    DEFAULT,

    /**
     * Project specific dashboard.
     */
    PROJECT,

    /**
     * User defined dashboard.
     */
    USER

}
