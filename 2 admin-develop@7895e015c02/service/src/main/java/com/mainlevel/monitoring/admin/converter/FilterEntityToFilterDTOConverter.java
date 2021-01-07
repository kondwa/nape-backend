/*
 * Copyright (c) 2018 Mainlevel Consulting AG. All rights reserved.
 */
package com.mainlevel.monitoring.admin.converter;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.admin.api.dto.filter.FilterDTO;
import com.mainlevel.monitoring.admin.repository.entity.FilterEntity;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;

/**
 * Converter converting {@link FilterEntity} to {@link FilterDTO}.
 */
@Component
public class FilterEntityToFilterDTOConverter extends AbstractApplicationAwareConverter<FilterEntity, FilterDTO> {

    @Override
    public FilterDTO convert(FilterEntity source) {
        FilterDTO target = super.getModelMapper().map(source, FilterDTO.class);
        return target;
    }

}
