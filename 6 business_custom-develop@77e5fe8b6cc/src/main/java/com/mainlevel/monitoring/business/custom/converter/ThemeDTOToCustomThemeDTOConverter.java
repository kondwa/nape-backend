/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.converter;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.admin.api.dto.theme.ThemeDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.user.CustomThemeDTO;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;

/**
 * Converter converting {@link ThemeDTO} to {@link CustomThemeDTO}.
 */
@Component
public class ThemeDTOToCustomThemeDTOConverter extends AbstractApplicationAwareConverter<ThemeDTO, CustomThemeDTO> {

    @Override
    public CustomThemeDTO convert(ThemeDTO source) {

        CustomThemeDTO result =
            CustomThemeDTO.builder().identifier(source.getIdentifier()).name(source.getName()).applicationTitle(source.getApplicationTitle())
                .logo(source.getLogo()).secondaryLogo(source.getSecondaryLogo()).styles(source.getStyles()).build();
        return result;
    }

}
