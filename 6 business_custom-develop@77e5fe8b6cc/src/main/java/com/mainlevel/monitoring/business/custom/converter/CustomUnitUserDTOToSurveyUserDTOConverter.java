/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.converter;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.business.custom.resource.dto.user.CustomUnitUserDTO;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.user.SurveyUserDTO;
import com.mainlevel.monitoring.survey.api.dto.user.SurveyUserType;

/**
 * Converter converting {@link CustomUnitUserDTO} to {@link SurveyUserDTO}.
 */
@Component
public class CustomUnitUserDTOToSurveyUserDTOConverter extends AbstractApplicationAwareConverter<CustomUnitUserDTO, SurveyUserDTO> {

    @Override
    public SurveyUserDTO convert(CustomUnitUserDTO source) {

        SurveyUserDTO result = SurveyUserDTO.builder().graphId(source.getGraphId()).name(source.getName()).username(source.getUsername())
            .userType(SurveyUserType.INTERNAL).participations(new ArrayList<>()).build();

        return result;
    }

}
