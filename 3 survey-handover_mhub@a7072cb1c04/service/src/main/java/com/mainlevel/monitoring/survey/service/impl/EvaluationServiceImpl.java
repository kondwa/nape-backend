/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mainlevel.monitoring.survey.database.queryresult.evaluation.EvaluationResult;
import com.mainlevel.monitoring.survey.database.repository.EvaluationRepository;
import com.mainlevel.monitoring.survey.service.EvaluationService;
import com.mainlevel.monitoring.survey.service.model.facet.FacetParams;

/**
 * Default implementation of {@link EvaluationService}.
 */
@Service
public class EvaluationServiceImpl implements EvaluationService {

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Override
    public List<Map<String, Object>> evaluateQuery(String query, Map<String, Object> parameters) {
        if (parameters == null) {
            parameters = Collections.emptyMap();
        }

        return evaluationRepository.executeQuery(query, parameters);
    }

    @Override
    public List<EvaluationResult> loadEvaluationResults(String queryName, FacetParams facets) {
        return evaluationRepository.findEvaluationResults(queryName, facets);
    }

}
