/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.converter;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.admin.api.dto.indicator.IndicatorGroupDTO;
import com.mainlevel.monitoring.admin.api.dto.unit.UnitDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.indicator.CustomIndicatorGroupDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.unit.CustomUnitDTO;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;

/**
 * Converter converting {@link UnitDTO} to {@link CustomUnitDTO}.
 */
@Component
public class IndicatorGroupDTOToCustomIndicatorGroupDTOConverter extends AbstractApplicationAwareConverter<IndicatorGroupDTO, CustomIndicatorGroupDTO> {

    @Override
    public CustomIndicatorGroupDTO convert(IndicatorGroupDTO source) {

        CustomIndicatorGroupDTO result = super.getModelMapper().map(source, CustomIndicatorGroupDTO.class);

        return result;
    }

}
