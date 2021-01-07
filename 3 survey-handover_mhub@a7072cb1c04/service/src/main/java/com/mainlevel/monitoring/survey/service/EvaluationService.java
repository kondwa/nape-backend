/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.service;

import java.util.List;
import java.util.Map;

import com.mainlevel.monitoring.survey.database.queryresult.evaluation.EvaluationResult;
import com.mainlevel.monitoring.survey.service.model.facet.FacetParams;

/**
 * Service for loading survey evaluation results.
 */
public interface EvaluationService {

    /**
     * Execute the given query and return the result list.
     *
     * @param query the query to execute
     * @param parameters query parameters
     * @return the result rows
     */
    List<Map<String, Object>> evaluateQuery(String query, Map<String, Object> parameters);

    /**
     * Load generic evaluation results based on a query name.
     *
     * @param queryName name of the evaluation query
     * @param facetParams the filter parameters
     * @return the list of events
     */
    List<EvaluationResult> loadEvaluationResults(String queryName, FacetParams facetParams);

}
