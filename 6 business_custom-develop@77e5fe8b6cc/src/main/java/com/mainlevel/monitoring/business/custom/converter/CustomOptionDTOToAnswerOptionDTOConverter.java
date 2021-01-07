/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.converter;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.business.custom.resource.dto.question.CustomOptionDTO;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.survey.question.AnswerOptionDTO;

/**
 * Converter converting {@link CustomOptionDTO} to {@link AnswerOptionDTO}.
 */
@Component
public class CustomOptionDTOToAnswerOptionDTOConverter extends AbstractApplicationAwareConverter<CustomOptionDTO, AnswerOptionDTO> {

    @Override
    public AnswerOptionDTO convert(CustomOptionDTO source) {

        AnswerOptionDTO result = AnswerOptionDTO.builder().graphId(source.getGraphId()).description(source.getDescription()).name(source.getName())
            .index(source.getIndex()).type(source.getType()).value(source.getValue()).build();

        return result;
    }

}
