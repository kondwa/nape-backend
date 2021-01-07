/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.admin.api.dto.indicator.IndicatorDTO;
import com.mainlevel.monitoring.admin.api.dto.indicator.IndicatorVisualizationDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.indicator.CustomIndicatorDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.indicator.CustomIndicatorVisualizationDTO;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;

/**
 * Converter converting {@link IndicatorDTO} to {@link CustomIndicatorDTO}.
 */
@Component
public class IndicatorDTOToCustomIndicatorDTOConverter extends AbstractApplicationAwareConverter<IndicatorDTO, CustomIndicatorDTO> {

    @Override
    public CustomIndicatorDTO convert(IndicatorDTO source) {

        List<CustomIndicatorDTO> subIndicators = super.getCollectionConversionService().convert(source.getSubIndicators(), CustomIndicatorDTO.class);

        List<CustomIndicatorVisualizationDTO> visualizations = convertVisualizations(source.getVisualizations());

        CustomIndicatorDTO result = CustomIndicatorDTO.builder().key(source.getKey()).name(source.getName()).description(source.getDescription())
            .subIndicators(subIndicators).visualizations(visualizations).build();

        return result;
    }

    /**
     * Convert the visualization object.
     *
     * @param visualization the admin visualization
     * @return the custom visualization
     */
    private List<CustomIndicatorVisualizationDTO> convertVisualizations(List<IndicatorVisualizationDTO> visualizations) {
        if (visualizations == null) {
            return null;
        }

        List<CustomIndicatorVisualizationDTO> resultList = new ArrayList<>();

        for (IndicatorVisualizationDTO source : visualizations) {
            CustomIndicatorVisualizationDTO result = CustomIndicatorVisualizationDTO.builder().chartTitle(source.getChartTitle())
                .chartType(source.getChartType()).icon(source.getIcon()).options(source.getOptions()).width(source.getWidth()).build();
            resultList.add(result);
        }

        return resultList;
    }

}
