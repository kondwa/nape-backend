/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.converter;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.business.custom.resource.dto.question.CustomOptionDTO;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.survey.question.AnswerOptionDTO;

/**
 * Converter converting {@link AnswerOptionDTO} to {@link CustomOptionDTO}.
 */
@Component
public class AnswerOptionDTOToCustomOptionDTOConverter extends AbstractApplicationAwareConverter<AnswerOptionDTO, CustomOptionDTO> {

    @Override
    public CustomOptionDTO convert(AnswerOptionDTO source) {

        CustomOptionDTO result = CustomOptionDTO.builder().graphId(source.getGraphId()).index(source.getIndex()).name(source.getName())
            .description(source.getDescription()).type(source.getType()).value(source.getValue()).build();

        return result;
    }

}
