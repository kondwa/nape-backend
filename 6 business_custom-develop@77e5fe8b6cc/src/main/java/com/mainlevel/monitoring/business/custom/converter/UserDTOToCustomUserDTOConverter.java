/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.authentication.api.dto.UserDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.dashboard.CustomDashboardNameDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.user.CustomRoleDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.user.CustomUserDTO;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;

/**
 * Converter converting {@link UserDTO} to {@link CustomUserDTO}.
 */
@Component
public class UserDTOToCustomUserDTOConverter extends AbstractApplicationAwareConverter<UserDTO, CustomUserDTO> {

    @Override
    public CustomUserDTO convert(UserDTO source) {

        List<CustomRoleDTO> roles = new ArrayList<>();

        source.getRoles().forEach(role -> {
            roles.add(CustomRoleDTO.builder().identifier(role.getIdentifier()).name(role.getName()).build());
        });

        List<CustomDashboardNameDTO> dashboards = new ArrayList<>();

        CustomUserDTO result = CustomUserDTO.builder().identifier(source.getIdentifier()).username(source.getUsername()).firstName(source.getFirstName())
            .lastName(source.getLastName()).active(source.getActive()).type(source.getType()).authentication(source.getAuthentication())
            .expiryTime(source.getExpiryTime()).roles(roles).dashboards(dashboards).build();

        return result;
    }

}
