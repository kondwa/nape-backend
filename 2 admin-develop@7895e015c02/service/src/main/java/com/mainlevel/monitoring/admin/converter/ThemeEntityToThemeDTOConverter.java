/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.admin.converter;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.admin.api.dto.theme.ThemeDTO;
import com.mainlevel.monitoring.admin.repository.entity.ThemeEntity;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;

/**
 * Converter converting {@link ThemeEntity} to {@link ThemeDTO}.
 */
@Component
public class ThemeEntityToThemeDTOConverter extends AbstractApplicationAwareConverter<ThemeEntity, ThemeDTO> {

    @Override
    public ThemeDTO convert(ThemeEntity source) {

        ThemeDTO result = ThemeDTO.builder().identifier(source.getId()).name(source.getName()).applicationTitle(source.getApplicationTitle())
            .logo(source.getLogo()).secondaryLogo(source.getSecondaryLogo()).styles(source.getStyles()).build();

        return result;
    }

}
