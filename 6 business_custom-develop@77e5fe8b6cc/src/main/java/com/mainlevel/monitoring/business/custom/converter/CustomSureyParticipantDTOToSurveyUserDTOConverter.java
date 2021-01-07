/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.converter;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.business.custom.resource.dto.user.CustomSureyParticipantDTO;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.user.SurveyUserDTO;

/**
 * Converter converting {@link CustomSureyParticipantDTO} to {@link SurveyUserDTO}.
 */
@Component
public class CustomSureyParticipantDTOToSurveyUserDTOConverter extends AbstractApplicationAwareConverter<CustomSureyParticipantDTO, SurveyUserDTO> {

    @Override
    public SurveyUserDTO convert(CustomSureyParticipantDTO source) {

        SurveyUserDTO result = SurveyUserDTO.builder().graphId(source.getGraphId()).name(source.getName()).username(source.getUsername())
            .participations(new ArrayList<>()).build();

        return result;
    }

}
