/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.converter;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.admin.api.dto.user.UserDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.user.CustomUnitUserDTO;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;

/**
 * Converter converting {@link CustomUnitUserDTO} to {@link UserDTO}.
 */
@Component
public class CustomUnitUserDTOToUserDTOConverter extends AbstractApplicationAwareConverter<CustomUnitUserDTO, UserDTO> {

    @Override
    public UserDTO convert(CustomUnitUserDTO source) {

        UserDTO result = UserDTO.builder().name(source.getName()).userName(source.getUsername()).participations(new ArrayList<>()).build();

        return result;
    }

}
