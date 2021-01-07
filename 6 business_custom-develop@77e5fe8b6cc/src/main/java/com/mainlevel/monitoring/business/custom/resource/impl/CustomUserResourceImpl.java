/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.resource.impl;

import static com.mainlevel.monitoring.common.constant.RoleConstant.ROLE_USER;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RestController;

import com.mainlevel.monitoring.admin.api.dto.theme.ThemeDTO;
import com.mainlevel.monitoring.admin.api.resource.ThemeResource;
import com.mainlevel.monitoring.authentication.api.dto.UserDTO;
import com.mainlevel.monitoring.authentication.api.resource.UserResource;
import com.mainlevel.monitoring.business.custom.resource.CustomUserResource;
import com.mainlevel.monitoring.business.custom.resource.dto.dashboard.CustomDashboardNameDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.user.CustomThemeDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.user.CustomUserDTO;

/**
 * Default implementation of {@link CustomUserResource}.
 */
@RestController
@Secured(ROLE_USER)
public class CustomUserResourceImpl implements CustomUserResource {

    private static final String DEFAULT_THEME = "ml";

    @Autowired
    private UserResource userResource;

    @Autowired
    private com.mainlevel.monitoring.admin.api.resource.UserResource adminUserResource;

    @Autowired
    private ThemeResource themeResource;

    @Autowired
    private ConversionService conversionService;

    @Override
    public ResponseEntity<CustomUserDTO> getCurrentUser() {

        ResponseEntity<UserDTO> response = userResource.getCurrentUser();
        CustomUserDTO userDTO = conversionService.convert(response.getBody(), CustomUserDTO.class);

        com.mainlevel.monitoring.admin.api.dto.user.UserDTO metaUser = adminUserResource.getCurrentUser().getBody();
        userDTO.setDefaultProject(metaUser.getDefaultProject());
        List<String> dashboards = metaUser.getDashboards();

        dashboards.stream().map(d -> CustomDashboardNameDTO.builder().id(d).build()).forEach(dn -> userDTO.getDashboards().add(dn));

        String themeName = response.getBody().getTheme() != null ? response.getBody().getTheme() : DEFAULT_THEME;

        ResponseEntity<ThemeDTO> themeResponse = themeResource.getTheme(themeName);
        CustomThemeDTO themeDTO = conversionService.convert(themeResponse.getBody(), CustomThemeDTO.class);
        userDTO.setTheme(themeDTO);

        return ResponseEntity.ok(userDTO);
    }

}
