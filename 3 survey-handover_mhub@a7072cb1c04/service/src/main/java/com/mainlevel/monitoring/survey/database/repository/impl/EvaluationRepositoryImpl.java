/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.database.repository.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StreamUtils;

import com.mainlevel.monitoring.survey.api.exception.EvaluationQueryNotFoundException;
import com.mainlevel.monitoring.survey.database.queryresult.evaluation.EvaluationResult;
import com.mainlevel.monitoring.survey.database.repository.EvaluationRepository;
import com.mainlevel.monitoring.survey.service.model.facet.FacetParams;

/**
 * Custom spring data repository implementation for loading generic evaluation results.
 */
@Repository
public class EvaluationRepositoryImpl implements EvaluationRepository {

    private Logger logger = LoggerFactory.getLogger(EvaluationRepositoryImpl.class);

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private Session neo4jSession;

    private static final String QUERY_FILE_SUFFIX = ".cql";

    private static final String FACET_TIMEINTERVALS = "timeIntervals";

    @Override
    public List<Map<String, Object>> executeQuery(String query, Map<String, Object> parameters) {
        Result result = neo4jSession.query(query, parameters, true);

        List<Map<String, Object>> resultList = new ArrayList<>();

        result.forEach(resultMap -> {
            resultList.add(Collections.unmodifiableMap(resultMap));
        });

        return resultList;
    }

    @Override
    public List<EvaluationResult> findEvaluationResults(String queryName, FacetParams facets) {

        String cypher = loadCypher(queryName);

        if (cypher == null) {
            throw new EvaluationQueryNotFoundException(queryName);
        }

        Map<String, Object> parameters = new HashMap<>();
        if (facets != null) {
            parameters.put(FACET_TIMEINTERVALS, facets.getTimeFacets());
        }

        Result result = neo4jSession.query(cypher, parameters);

        List<EvaluationResult> resultList = new ArrayList<>();

        result.forEach((resultMap) -> {
            String title = (String) resultMap.get("title");
            String recordName = (String) resultMap.get("recordName");
            String xAxisValue = (String) resultMap.get("xAxisValue");
            Number yAxisValue = (Number) resultMap.get("yAxisValue");
            String xAxisDesc = (String) resultMap.get("xAxisDesc");
            String yAxisDesc = (String) resultMap.get("yAxisDesc");

            EvaluationResult evaluationResult = EvaluationResult.builder().title(title).recordName(recordName).xAxisValue(xAxisValue)
                .xAxisDesc(xAxisDesc).yAxisValue(yAxisValue).yAxisDesc(yAxisDesc).build();

            resultList.add(evaluationResult);
        });

        return resultList;
    }

    /**
     * Loads and parses the query file with the given name from classpath and stores its content in cache.
     *
     * @param name name of the query
     * @return the query string
     */
    @Cacheable(value = "cypherQueriesCache", key = "#name", unless = "#result != null")
    private String loadCypher(String name) {

        try {
            logger.debug("Cypher query with name {} not found in cache, try loading it from file.", name);

            ResourcePatternResolver resolver = ResourcePatternUtils.getResourcePatternResolver(this.resourceLoader);
            Resource[] queryFiles = resolver.getResources("classpath*:queries/*.cql");

            for (Resource queryFile : queryFiles) {

                if (queryFile.getFilename().endsWith(name + QUERY_FILE_SUFFIX)) {

                    logger.debug("Found query file with name {} in classpath.", queryFile.getFilename());

                    InputStream inputStream = queryFile.getInputStream();
                    String cypher = StreamUtils.copyToString(inputStream, Charset.defaultCharset());

                    return cypher;
                }
            }

            logger.warn("Query file with name {} not found in classpath.", name + QUERY_FILE_SUFFIX);

            return null;
        } catch (IOException e) {
            throw new IllegalStateException("Invalid query file path.", e);
        }
    }

}
