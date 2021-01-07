/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.api.resource;

import static com.mainlevel.monitoring.survey.api.Survey.SERVICE_NAME;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mainlevel.monitoring.survey.api.dto.evaluation.TimeFacetsListDTO;

/**
 * Resource for providing facets for evaluations.
 */
@FeignClient(SERVICE_NAME)
public interface EvaluationFacetsResource {

    /** Resource Mapping URI */
    final String URI = "/evaluations/facets";

    /**
     * Listing the time facet options.
     *
     * @return the list of facets
     */
    @RequestMapping(value = URI + "/time", method = GET, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<TimeFacetsListDTO> getTimeFacets();

}
