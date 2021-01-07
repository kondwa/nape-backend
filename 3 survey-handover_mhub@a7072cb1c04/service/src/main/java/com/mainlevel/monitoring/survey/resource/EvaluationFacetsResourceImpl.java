/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.resource;

import static com.mainlevel.monitoring.common.constant.RoleConstant.ROLE_USER;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RestController;

import com.mainlevel.monitoring.common.service.CollectionConversionService;
import com.mainlevel.monitoring.survey.api.dto.evaluation.TimeFacetsDTO;
import com.mainlevel.monitoring.survey.api.dto.evaluation.TimeFacetsListDTO;
import com.mainlevel.monitoring.survey.api.resource.EvaluationFacetsResource;
import com.mainlevel.monitoring.survey.service.FacetService;
import com.mainlevel.monitoring.survey.service.model.facet.TimeFacetGroup;

/**
 * Default implementation for {@link EvaluationFacetsResource}.
 */
@RestController
@Secured(ROLE_USER)
public class EvaluationFacetsResourceImpl implements EvaluationFacetsResource {

    @Autowired
    private FacetService facetService;

    @Autowired
    private CollectionConversionService conversionService;

    @Override
    public ResponseEntity<TimeFacetsListDTO> getTimeFacets() {

        List<TimeFacetGroup> facets = facetService.loadTimeFacets();
        List<TimeFacetsDTO> facetDTOs = conversionService.convert(facets, TimeFacetsDTO.class);

        TimeFacetsListDTO dto = TimeFacetsListDTO.builder().timeFacets(facetDTOs).build();

        return ResponseEntity.ok(dto);
    }

}
