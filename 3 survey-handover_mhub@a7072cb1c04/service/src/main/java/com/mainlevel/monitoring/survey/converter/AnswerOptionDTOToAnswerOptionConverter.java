/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.converter;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.survey.question.AnswerOptionDTO;
import com.mainlevel.monitoring.survey.database.node.AnswerOption;

/**
 * Converter converting {@link AnswerOptionDTO} to {@link AnswerOption}.
 */
@Component
public class AnswerOptionDTOToAnswerOptionConverter extends AbstractApplicationAwareConverter<AnswerOptionDTO, AnswerOption> {

    @Override
    public AnswerOption convert(AnswerOptionDTO source) {

        if (source != null) {

            AnswerOption option = AnswerOption.builder().gid(source.getGraphId()).name(source.getName()).description(source.getDescription())
                .type(source.getType()).index(source.getIndex()).value(source.getValue()).build();

            return option;
        }

        return null;
    }
}
