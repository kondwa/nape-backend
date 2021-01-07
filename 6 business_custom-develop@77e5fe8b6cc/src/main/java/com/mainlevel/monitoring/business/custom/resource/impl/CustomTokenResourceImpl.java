/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.resource.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.mainlevel.monitoring.authentication.api.dto.TokenDTO;
import com.mainlevel.monitoring.authentication.api.resource.TokenResource;
import com.mainlevel.monitoring.business.custom.resource.CustomTokenResource;
import com.mainlevel.monitoring.business.custom.resource.dto.CustomTokenDTO;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of {@link CustomTokenResource}.
 */
@Slf4j
@RestController
public class CustomTokenResourceImpl implements CustomTokenResource {

    @Autowired
    private TokenResource tokenClient;

    @Autowired
    private ConversionService conversionService;

    @Override
    public ResponseEntity<CustomTokenDTO> doLogin() {

        ResponseEntity<TokenDTO> tokenResponse = tokenClient.doLogin();
        TokenDTO token = tokenResponse.getBody();

        log.info("CUSTOM Login of user {} successful.", token.getUserName());

        CustomTokenDTO dto = conversionService.convert(token, CustomTokenDTO.class);

        return ResponseEntity.ok(dto);
    }

}
