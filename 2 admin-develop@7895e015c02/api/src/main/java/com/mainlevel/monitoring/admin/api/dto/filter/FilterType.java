/*
 * Copyright (c) 2018 Mainlevel Consulting AG. All rights reserved.
 */
package com.mainlevel.monitoring.admin.api.dto.filter;

/**
 * Type of filter.
 */
public enum FilterType {

    /**
     * Filter is of type date.
     */
    DATE,

    /**
     * Filter is of type selection, only one selection allowed.
     */
    SINGLE_SELECTION,

    /**
     * Filter is of type selection, multiple selections allowed.
     */
    MULTI_SELECTION;

}
