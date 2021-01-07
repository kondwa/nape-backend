/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.converter;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.survey.question.AnswerOptionDTO;
import com.mainlevel.monitoring.survey.database.node.AnswerOption;

/**
 * Converter converting {@link AnswerOption} to {@link AnswerOptionDTO}.
 */
@Component
public class AnswerOptionToAnswerOptionDTOConverter extends AbstractApplicationAwareConverter<AnswerOption, AnswerOptionDTO> {

    @Override
    public AnswerOptionDTO convert(AnswerOption source) {

        if (source != null) {
            AnswerOptionDTO option = AnswerOptionDTO.builder().graphId(source.getGid()).index(source.getIndex()).type(source.getType())
                .name(source.getName()).value(source.getValue()).description(source.getDescription()).build();

            return option;
        }

        return null;
    }
}
