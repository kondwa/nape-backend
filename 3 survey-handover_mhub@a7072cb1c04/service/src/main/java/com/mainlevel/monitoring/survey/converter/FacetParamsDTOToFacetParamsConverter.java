/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.evaluation.facet.FacetParamsDTO;
import com.mainlevel.monitoring.survey.service.model.facet.FacetParams;
import com.mainlevel.monitoring.survey.service.model.facet.TimeFacet;

/**
 * Converter for converting FacetParamsDTO to FacetParams.
 */
@Component
public class FacetParamsDTOToFacetParamsConverter extends AbstractApplicationAwareConverter<FacetParamsDTO, FacetParams> {

    @Override
    public FacetParams convert(FacetParamsDTO source) {

        List<TimeFacet> facets = new ArrayList<>();
        if (source.getTimeFacets() != null) {
            source.getTimeFacets().forEach(facetDTO -> {
                TimeFacet facet = TimeFacet.builder().startTime(facetDTO.getFrom()).endTime(facetDTO.getTo()).build();
                facets.add(facet);
            });
        }

        FacetParams result = FacetParams.builder().timeFacets(facets).build();

        return result;
    }

}
