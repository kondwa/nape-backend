/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.converter;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.business.custom.resource.dto.answer.CustomAnswerDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.question.CustomOptionDTO;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.survey.answer.SurveyAnswerDTO;

/**
 * Converter converting {@link SurveyAnswerDTO} to {@link CustomAnswerDTO}.
 */
@Component
public class SurveyAnswerDTOToCustomAnswerDTOConverter extends AbstractApplicationAwareConverter<SurveyAnswerDTO, CustomAnswerDTO> {

    @Override
    public CustomAnswerDTO convert(SurveyAnswerDTO source) {

        CustomOptionDTO option = super.getConversionService().convert(source.getOption(), CustomOptionDTO.class);

        CustomAnswerDTO result =
            CustomAnswerDTO.builder().graphId(source.getGraphId()).type(source.getType()).option(option).value(source.getValue()).build();

        return result;
    }

}
