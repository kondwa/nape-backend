/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.converter;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.business.custom.resource.dto.answer.CustomAnswerDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.answer.CustomQuestionAnswerDTO;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.survey.answer.SurveyAnswerDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.question.SurveyQuestionAnswerDTO;

/**
 * Converter converting {@link SurveyAnswerDTO} to {@link CustomAnswerDTO}.
 */
@Component
public class SurveyQuestionAnswerDTOToCustomQuestionAnswerDTOConverter
    extends AbstractApplicationAwareConverter<SurveyQuestionAnswerDTO, CustomQuestionAnswerDTO> {

    @Override
    public CustomQuestionAnswerDTO convert(SurveyQuestionAnswerDTO source) {

        CustomQuestionAnswerDTO result = null;

        if (source != null) {

            result = CustomQuestionAnswerDTO.builder().title(source.getTitle()).order(source.getOrder()).build();

            if (source.getAnswer() != null) {
                CustomAnswerDTO answer = super.getConversionService().convert(source.getAnswer(), CustomAnswerDTO.class);
                result.setAnswer(answer);
            }

        }

        return result;
    }

}
