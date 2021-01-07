/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */
package com.mainlevel.monitoring.admin.converter;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.admin.api.dto.language.LanguageDTO;
import com.mainlevel.monitoring.admin.repository.entity.LanguageEntity;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;

/**
 * Converter converting {@link LanguageEntity} to {@link LanguageDTO}.
 */
@Component
public class LanguageEntityToLanguageDTOConverter extends AbstractApplicationAwareConverter<LanguageEntity, LanguageDTO> {

    @Override
    public LanguageDTO convert(LanguageEntity source) {

        LanguageDTO result = LanguageDTO.builder().id(source.getId()).name(source.getName()).build();

        return result;
    }

}
