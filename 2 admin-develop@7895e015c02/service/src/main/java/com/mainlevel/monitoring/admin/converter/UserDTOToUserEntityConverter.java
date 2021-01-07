/*
 * Copyright (c) 2018 Mainlevel Consulting AG. All rights reserved.
 */
package com.mainlevel.monitoring.admin.converter;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.admin.api.dto.user.UserDTO;
import com.mainlevel.monitoring.admin.repository.entity.UserEntity;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;

/**
 * Converter converting {@link UserDTO} to {@link UserEntity}.
 */
@Component
public class UserDTOToUserEntityConverter extends AbstractApplicationAwareConverter<UserDTO, UserEntity> {

    @Override
    public UserEntity convert(UserDTO source) {

        UserEntity entity = super.getModelMapper().map(source, UserEntity.class);

        return entity;
    }

}
