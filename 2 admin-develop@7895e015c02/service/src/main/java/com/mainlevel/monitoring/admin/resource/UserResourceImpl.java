/*
 * Copyright (c) 2018 Mainlevel Consulting AG. All rights reserved.
 */
package com.mainlevel.monitoring.admin.resource;

import static com.mainlevel.monitoring.common.constant.RoleConstant.ROLE_USER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RestController;

import com.mainlevel.monitoring.admin.api.dto.user.UserDTO;
import com.mainlevel.monitoring.admin.api.resource.UserResource;
import com.mainlevel.monitoring.admin.repository.entity.UserEntity;
import com.mainlevel.monitoring.admin.service.UserService;
import com.mainlevel.monitoring.common.service.AuthenticationAccessService;

/**
 * Default implementation of {@link UserResource}.
 */
@RestController
@Secured(ROLE_USER)
public class UserResourceImpl implements UserResource {

    @Autowired
    private AuthenticationAccessService accessService;

    @Autowired
    private UserService userService;

    @Autowired
    private ConversionService conversionService;

    @Override
    public ResponseEntity<UserDTO> getCurrentUser() {

        String username = accessService.getCurrentUsername();

        UserEntity userEntity = userService.findByUserName(username);

        UserDTO userDTO = conversionService.convert(userEntity, UserDTO.class);

        return ResponseEntity.ok(userDTO);
    }

    @Override
    public ResponseEntity<UserDTO> createUser(UserDTO user) {

        UserEntity userEntity = conversionService.convert(user, UserEntity.class);

        userEntity = userService.save(userEntity);

        UserDTO userDTO = conversionService.convert(userEntity, UserDTO.class);

        return ResponseEntity.ok(userDTO);
    }

}
