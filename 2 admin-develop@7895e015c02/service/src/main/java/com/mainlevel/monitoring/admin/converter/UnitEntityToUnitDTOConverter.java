/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.admin.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.admin.api.dto.unit.UnitDTO;
import com.mainlevel.monitoring.admin.api.dto.unit.UnitLinkDTO;
import com.mainlevel.monitoring.admin.repository.entity.UnitEntity;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;

/**
 * Converter converting {@link UnitEntity} to {@link UnitDTO}.
 */
@Component
public class UnitEntityToUnitDTOConverter extends AbstractApplicationAwareConverter<UnitEntity, UnitDTO> {

    @Override
    public UnitDTO convert(UnitEntity source) {

        List<UnitDTO> childrenDTO = null;

        if (source.getChildren() != null) {
            childrenDTO = new ArrayList<>();
            for (UnitEntity child : source.getChildren()) {
                UnitDTO childDTO = convert(child);
                childrenDTO.add(childDTO);
            }
        }

        String parentId = source.getParent() != null ? source.getParent().getId() : null;

        List<UnitLinkDTO> links = new ArrayList<>();
        if (source.getLinks() != null) {
            source.getLinks().forEach(link -> {
                links.add(super.getModelMapper().map(link, UnitLinkDTO.class));
            });
        }

        UnitDTO unitDTO = UnitDTO.builder().id(source.getId()).name(source.getName()).foreignId(source.getForeignId()).type(source.getType())
            .parentId(parentId).children(childrenDTO).dashboards(source.getDashboards()).links(links).build();

        return unitDTO;
    }

}
