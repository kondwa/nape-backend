/*
 * Copyright (c) 2018 Mainlevel Consulting AG. All rights reserved.
 */
package com.mainlevel.monitoring.admin.converter;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.admin.api.dto.user.UserDTO;
import com.mainlevel.monitoring.admin.repository.entity.UserEntity;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;

/**
 * Converter converting {@link UserEntity} to {@link UserDTO}.
 */
@Component
public class UserEntityToUserDTOConverter extends AbstractApplicationAwareConverter<UserEntity, UserDTO> {

    @Override
    public UserDTO convert(UserEntity source) {

        UserDTO dto = super.getModelMapper().map(source, UserDTO.class);

        return dto;
    }

}
