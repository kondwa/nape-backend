/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.converter;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.admin.api.dto.language.LanguageDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.CustomLanguageDTO;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;

/**
 * Converter convetring {@link LanguageDTO} to {@link CustomLanguageDTO}.
 */
@Component
public class LanguageDTOToCustomLanguageDTOConverter extends AbstractApplicationAwareConverter<LanguageDTO, CustomLanguageDTO> {

    @Override
    public CustomLanguageDTO convert(LanguageDTO source) {
        return CustomLanguageDTO.builder().id(source.getId()).name(source.getName()).build();
    }

}
