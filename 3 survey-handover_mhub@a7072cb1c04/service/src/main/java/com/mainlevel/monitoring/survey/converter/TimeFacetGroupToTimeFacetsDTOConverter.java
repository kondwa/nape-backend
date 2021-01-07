/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.evaluation.TimeFacetIntervalType;
import com.mainlevel.monitoring.survey.api.dto.evaluation.TimeFacetValueDTO;
import com.mainlevel.monitoring.survey.api.dto.evaluation.TimeFacetsDTO;
import com.mainlevel.monitoring.survey.service.model.facet.TimeFacet;
import com.mainlevel.monitoring.survey.service.model.facet.TimeFacetGroup;

/**
 * Converter for converting TimeFacetGroup to TimeFacetsDTO.
 */
@Component
public class TimeFacetGroupToTimeFacetsDTOConverter extends AbstractApplicationAwareConverter<TimeFacetGroup, TimeFacetsDTO> {

    @Override
    public TimeFacetsDTO convert(TimeFacetGroup source) {

        List<TimeFacetValueDTO> facetValues = new ArrayList<>();

        for (TimeFacet facet : source.getFacets()) {
            TimeFacetValueDTO valueDTO =
                TimeFacetValueDTO.builder().startTime(facet.getStartTime()).endTime(facet.getEndTime()).label(facet.getLabel()).build();

            facetValues.add(valueDTO);
        }

        TimeFacetIntervalType type = null;

        switch (source.getType()) {
            case YEARS:
                type = TimeFacetIntervalType.YEARS;
                break;
        }

        TimeFacetsDTO result = TimeFacetsDTO.builder().intervalType(type).facets(facetValues).build();

        return result;
    }

}
