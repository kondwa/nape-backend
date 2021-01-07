/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.admin.resource;

import static com.mainlevel.monitoring.common.constant.RoleConstant.ROLE_USER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mainlevel.monitoring.admin.api.dto.theme.ThemeDTO;
import com.mainlevel.monitoring.admin.api.resource.ThemeResource;
import com.mainlevel.monitoring.admin.repository.entity.ThemeEntity;
import com.mainlevel.monitoring.admin.service.ThemeService;

/**
 * Default implementation of {@link ThemeResource}.
 */
@RestController
@Secured(ROLE_USER)
public class ThemeResourceImpl implements ThemeResource {

    @Autowired
    private ThemeService themeService;

    @Autowired
    private ConversionService conversionService;

    @Override
    public ResponseEntity<ThemeDTO> getTheme(@PathVariable("name") String name) {

        ThemeEntity themeEntity = themeService.loadThemeByName(name);

        ThemeDTO themeDTO = conversionService.convert(themeEntity, ThemeDTO.class);

        return ResponseEntity.ok(themeDTO);

    }

}
