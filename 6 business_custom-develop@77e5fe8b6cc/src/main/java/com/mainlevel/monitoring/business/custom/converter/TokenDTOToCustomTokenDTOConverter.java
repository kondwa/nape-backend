/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.converter;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.authentication.api.dto.TokenDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.CustomTokenDTO;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;

/**
 * Converter converting authentication {@link TokenDTO} to {@link CustomTokenDTO}.
 */
@Component
public class TokenDTOToCustomTokenDTOConverter extends AbstractApplicationAwareConverter<TokenDTO, CustomTokenDTO> {

    @Override
    public CustomTokenDTO convert(TokenDTO source) {

        CustomTokenDTO result = CustomTokenDTO.builder().token(source.getToken()).userName(source.getUserName()).user(source.getUser()).build();

        return result;
    }

}
