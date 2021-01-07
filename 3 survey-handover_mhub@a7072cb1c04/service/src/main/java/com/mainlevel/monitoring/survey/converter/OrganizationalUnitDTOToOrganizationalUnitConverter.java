/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.converter;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.OrganizationalUnitDTO;
import com.mainlevel.monitoring.survey.database.node.OrganizationalUnit;

/**
 * Converter converting {@link OrganizationalUnitDTO} to {@link OrganizationalUnit}.
 */
@Component
public class OrganizationalUnitDTOToOrganizationalUnitConverter extends AbstractApplicationAwareConverter<OrganizationalUnitDTO, OrganizationalUnit> {

    @Override
    public OrganizationalUnit convert(OrganizationalUnitDTO source) {

        OrganizationalUnit parent = null;

        if (source.getParent() != null) {
            parent = convert(source.getParent());
        }

        OrganizationalUnit result = OrganizationalUnit.builder().gid(source.getGraphId()).foreignId(source.getForeignId()).name(source.getName())
            .type(source.getType()).refId(source.getRefId()).parent(parent).build();

        return result;
    }

}
