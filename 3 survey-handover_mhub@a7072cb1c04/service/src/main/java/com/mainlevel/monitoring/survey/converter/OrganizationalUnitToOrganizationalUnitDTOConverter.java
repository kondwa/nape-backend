/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.converter;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.OrganizationalUnitDTO;
import com.mainlevel.monitoring.survey.database.node.OrganizationalUnit;

/**
 * Converter converting {@link OrganizationalUnit} to {@link OrganizationalUnitDTO}.
 */
@Component
public class OrganizationalUnitToOrganizationalUnitDTOConverter extends AbstractApplicationAwareConverter<OrganizationalUnit, OrganizationalUnitDTO> {

    @Override
    public OrganizationalUnitDTO convert(OrganizationalUnit source) {

        OrganizationalUnitDTO parent = null;

        if (source.getParent() != null) {
            parent = convert(source.getParent());
        }

        OrganizationalUnitDTO result = OrganizationalUnitDTO.builder().graphId(source.getGid()).foreignId(source.getForeignId())
            .name(source.getName()).refId(source.getRefId()).type(source.getType()).parent(parent).build();

        return result;
    }

}
