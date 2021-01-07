/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.resource.impl;

import static com.mainlevel.monitoring.common.constant.RoleConstant.ROLE_USER;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RestController;

import com.mainlevel.monitoring.admin.api.dto.language.LanguageListDTO;
import com.mainlevel.monitoring.admin.api.resource.LanguageResource;
import com.mainlevel.monitoring.business.custom.resource.CustomLanguageResource;
import com.mainlevel.monitoring.business.custom.resource.dto.CustomLanguageDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.CustomLanguageListDTO;
import com.mainlevel.monitoring.common.service.CollectionConversionService;

/**
 * Default implementation of {@link CustomLanguageResource}.
 */
@RestController
@Secured(ROLE_USER)
public class CustomLanguageResourceImpl implements CustomLanguageResource {

    @Autowired
    private LanguageResource languageResource;

    @Autowired
    private CollectionConversionService collectionConversionService;

    @Override
    public ResponseEntity<CustomLanguageListDTO> findAll() {
        ResponseEntity<LanguageListDTO> languageResponse = languageResource.findAll();

        List<CustomLanguageDTO> languages = collectionConversionService.convert(languageResponse.getBody().getLanguages(), CustomLanguageDTO.class);

        CustomLanguageListDTO resultDTO = CustomLanguageListDTO.builder().languages(languages).build();

        return ResponseEntity.ok(resultDTO);

    }

}
