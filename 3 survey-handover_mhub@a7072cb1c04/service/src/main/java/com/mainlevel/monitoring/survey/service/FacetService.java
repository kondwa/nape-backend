/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.service;

import java.util.List;

import com.mainlevel.monitoring.survey.service.model.facet.TimeFacetGroup;

/**
 * Service for loading facets for filtering.
 */
public interface FacetService {

    /**
     * Loads the distinct time faceds based on existing reporting periods.
     *
     * @return the list of time facets grouped by interval
     */
    List<TimeFacetGroup> loadTimeFacets();
}
