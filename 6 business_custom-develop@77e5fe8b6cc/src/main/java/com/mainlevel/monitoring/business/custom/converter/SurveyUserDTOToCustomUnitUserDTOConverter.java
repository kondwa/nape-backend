/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.converter;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.business.custom.resource.dto.user.CustomUnitUserDTO;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.user.SurveyUserDTO;

/**
 * Converter converting {@link SurveyUserDTO} to {@link CustomUnitUserDTO}.
 */
@Component
public class SurveyUserDTOToCustomUnitUserDTOConverter extends AbstractApplicationAwareConverter<SurveyUserDTO, CustomUnitUserDTO> {

    @Override
    public CustomUnitUserDTO convert(SurveyUserDTO source) {

        CustomUnitUserDTO result = CustomUnitUserDTO.builder().graphId(source.getGraphId()).name(source.getName()).username(source.getUsername())
            .roles(new ArrayList<>()).build();

        return result;
    }

}
