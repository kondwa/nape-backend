/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.resource.impl;

import static com.mainlevel.monitoring.common.constant.RoleConstant.ROLE_USER;
import static org.springframework.http.HttpStatus.OK;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RestController;

import com.mainlevel.monitoring.business.custom.resource.CustomEntryResource;
import com.mainlevel.monitoring.common.resource.dto.NavigationDTO;

/**
 * Default implementation of {@link CustomEntryResource}.
 */
@RestController
@Secured(ROLE_USER)
public class CustomEntryResourceImpl implements CustomEntryResource {

    @Override
    public ResponseEntity<NavigationDTO> getNavigation() {
        final NavigationDTO navigationDTO = NavigationDTO.builder().build();
        return new ResponseEntity<>(navigationDTO, OK);
    }

}
