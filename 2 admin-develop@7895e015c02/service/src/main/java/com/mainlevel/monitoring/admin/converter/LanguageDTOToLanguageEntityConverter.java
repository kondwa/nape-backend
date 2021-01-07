/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */
package com.mainlevel.monitoring.admin.converter;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.admin.api.dto.language.LanguageDTO;
import com.mainlevel.monitoring.admin.repository.entity.LanguageEntity;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;

/**
 * Converter converting {@link LanguageDTO} to {@link LanguageEntity}.
 */
@Component
public class LanguageDTOToLanguageEntityConverter extends AbstractApplicationAwareConverter<LanguageDTO, LanguageEntity> {

    @Override
    public LanguageEntity convert(LanguageDTO source) {
        LanguageEntity result = LanguageEntity.builder().id(source.getId()).name(source.getName()).build();
        return result;
    }

}
