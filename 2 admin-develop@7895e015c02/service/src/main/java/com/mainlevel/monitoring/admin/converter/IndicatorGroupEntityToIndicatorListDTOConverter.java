/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.admin.converter;

import java.util.List;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.admin.api.dto.filter.FilterDTO;
import com.mainlevel.monitoring.admin.api.dto.indicator.IndicatorDTO;
import com.mainlevel.monitoring.admin.api.dto.indicator.IndicatorListDTO;
import com.mainlevel.monitoring.admin.repository.entity.IndicatorGroupEntity;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;

/**
 * Converter converting {@link IndicatorGroupEntity} to {@link IndicatorListDTO}.
 */
@Component
public class IndicatorGroupEntityToIndicatorListDTOConverter extends AbstractApplicationAwareConverter<IndicatorGroupEntity, IndicatorListDTO> {

    @Override
    public IndicatorListDTO convert(IndicatorGroupEntity source) {

        String unitName = source.getUnit() != null ? source.getUnit().getName() : null;
        List<IndicatorDTO> indicators = super.getCollectionConversionService().convert(source.getIndicators(), IndicatorDTO.class);
        List<FilterDTO> filters = super.getCollectionConversionService().convert(source.getFilters(), FilterDTO.class);

        IndicatorListDTO target =
            IndicatorListDTO.builder().groupName(source.getName()).unitName(unitName).indicators(indicators).filters(filters).build();

        return target;
    }

}
