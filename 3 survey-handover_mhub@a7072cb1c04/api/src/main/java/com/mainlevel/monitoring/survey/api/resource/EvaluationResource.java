/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.api.resource;

import static com.mainlevel.monitoring.survey.api.Survey.SERVICE_NAME;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mainlevel.monitoring.survey.api.dto.evaluation.EvaluationRequestDTO;
import com.mainlevel.monitoring.survey.api.dto.evaluation.EvaluationResponseDTO;
import com.mainlevel.monitoring.survey.api.dto.evaluation.EvaluationResultListDTO;
import com.mainlevel.monitoring.survey.api.dto.evaluation.facet.FacetParamsDTO;

/**
 * REST resource for survey evaluations.
 */
@FeignClient(SERVICE_NAME)
public interface EvaluationResource {

    /** Resource Mapping URI */
    final String URI = "/evaluations";

    /**
     * Loads results of an evaluation query with the given name for data visualization / evaluation.
     *
     * @param queryName name of the evaluation query
     * @param facets the facets for filtering
     * @return results of the evaluation query
     */
    @RequestMapping(value = URI + "/{queryName}", method = GET, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<EvaluationResultListDTO> loadEvaluationResults(@PathVariable("queryName") String queryName, FacetParamsDTO facets);

    /**
     * Evaluating a Neo4j query.
     *
     * @param body the query options
     * @return the query result
     */
    @RequestMapping(value = URI, method = POST, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<EvaluationResponseDTO> evaluateQuery(@RequestBody EvaluationRequestDTO body);

}
