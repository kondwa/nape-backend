/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.converter;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.admin.api.dto.unit.UnitDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.unit.CustomUnitDTO;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.OrganizationalUnitDTO;

/**
 * Converter converting {@link UnitDTO} to {@link CustomUnitDTO}.
 */
@Component
public class UnitDTOToOrganizationalUnitDTOConverter extends AbstractApplicationAwareConverter<UnitDTO, OrganizationalUnitDTO> {

    @Override
    public OrganizationalUnitDTO convert(UnitDTO source) {

        OrganizationalUnitDTO result = OrganizationalUnitDTO.builder().foreignId(source.getForeignId()).name(source.getName()).type(source.getType())
            .refId(source.getId()).build();

        return result;
    }

}
