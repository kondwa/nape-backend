/*
 * 0 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.admin.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.admin.api.dto.indicator.IndicatorDTO;
import com.mainlevel.monitoring.admin.api.dto.indicator.IndicatorVisualizationDTO;
import com.mainlevel.monitoring.admin.repository.entity.IndicatorEntity;
import com.mainlevel.monitoring.admin.repository.entity.IndicatorVisualizationEntity;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;

/**
 * Converter converting {@link IndicatorEntity} to {@link IndicatorDTO}.
 */
@Component
public class IndicatorEntityToIndicatorDTOConverter extends AbstractApplicationAwareConverter<IndicatorEntity, IndicatorDTO> {

    @Override
    public IndicatorDTO convert(IndicatorEntity source) {

        List<IndicatorVisualizationDTO> visualizations = convertVisualizations(source.getVisualizations());
        List<IndicatorDTO> subIndicators = super.getCollectionConversionService().convert(source.getSubIndicators(), IndicatorDTO.class);

        IndicatorDTO result = IndicatorDTO.builder().key(source.getKey()).name(source.getName()).description(source.getDescription())
            .dataQuery(source.getDataQuery()).visualizations(visualizations).subIndicators(subIndicators).build();

        return result;
    }

    /**
     * Converts the list of visualization entities to DTO.
     *
     * @param source visualization entity
     * @return visualization DTO
     */
    private List<IndicatorVisualizationDTO> convertVisualizations(List<IndicatorVisualizationEntity> sourceList) {
        if (sourceList == null) {
            return null;
        }

        List<IndicatorVisualizationDTO> resultList = new ArrayList<>();

        for (IndicatorVisualizationEntity source : sourceList) {
            IndicatorVisualizationDTO result = IndicatorVisualizationDTO.builder().chartTitle(source.getChartTitle()).chartType(source.getChartType())
                .icon(source.getIcon()).options(source.getOptions()).width(source.getWidth()).build();

            resultList.add(result);
        }

        return resultList;
    }

}
