/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.admin.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.admin.api.dto.unit.UnitDTO;
import com.mainlevel.monitoring.admin.repository.entity.UnitEntity;
import com.mainlevel.monitoring.admin.repository.entity.UnitLinkEntity;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;

/**
 * Converter converting {@link UnitDTO} to {@link UnitEntity}.
 */
@Component
public class UnitDTOToUnitEntityConverter extends AbstractApplicationAwareConverter<UnitDTO, UnitEntity> {

    @Override
    public UnitEntity convert(UnitDTO source) {

        UnitEntity parent = source.getParentId() != null ? UnitEntity.builder().id(source.getParentId()).build() : null;

        List<UnitLinkEntity> links = new ArrayList<>();
        if (source.getLinks() != null) {
            source.getLinks().forEach(link -> {
                links.add(super.getModelMapper().map(link, UnitLinkEntity.class));
            });
        }

        UnitEntity entity = UnitEntity.builder().id(source.getId()).name(source.getName()).foreignId(source.getForeignId()).type(source.getType())
            .parent(parent).dashboards(source.getDashboards()).links(links).build();

        if (source.getChildren() != null) {
            List<UnitEntity> childEntities = new ArrayList<>();
            for (UnitDTO child : source.getChildren()) {
                UnitEntity childEntity = convert(child);
                childEntity.setParent(entity);
                childEntities.add(childEntity);
            }

            entity.setChildren(childEntities);
        }

        return entity;
    }

}
