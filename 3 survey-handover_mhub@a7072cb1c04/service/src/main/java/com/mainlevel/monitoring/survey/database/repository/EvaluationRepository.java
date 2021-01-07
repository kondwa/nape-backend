/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.database.repository;

import java.util.List;
import java.util.Map;

import com.mainlevel.monitoring.survey.database.queryresult.evaluation.EvaluationResult;
import com.mainlevel.monitoring.survey.service.model.facet.FacetParams;

/**
 * A graph repository for loading survey evaluation data.
 */
public interface EvaluationRepository {

    /**
     * Execute the given cypher query.
     *
     * @param query the cypher query to execute as string
     * @param parameters the query parameters
     * @return the list of result rows
     */
    List<Map<String, Object>> executeQuery(String query, Map<String, Object> parameters);

    /**
     * Find the evaluation results for the given query.
     *
     * @param queryName name of the query
     * @param facets filtering facets
     * @return the list of evaluation results
     */
    List<EvaluationResult> findEvaluationResults(String queryName, FacetParams facets);
}
