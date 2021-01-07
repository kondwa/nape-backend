/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.converter;

import java.util.List;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.admin.api.dto.unit.UnitDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.unit.CustomUnitDTO;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;

/**
 * Converter converting {@link CustomUnitDTO} to {@link UnitDTO}.
 */
@Component
public class CustomUnitDTOToUnitDTOConverter extends AbstractApplicationAwareConverter<CustomUnitDTO, UnitDTO> {

    @Override
    public UnitDTO convert(CustomUnitDTO source) {

        List<UnitDTO> children = super.getCollectionConversionService().convert(source.getChildren(), UnitDTO.class);

        UnitDTO result = UnitDTO.builder().foreignId(source.getForeignId()).name(source.getName()).id(source.getId())
            .parentId(source.getParentId()).type(source.getType()).children(children).build();

        return result;
    }

}
