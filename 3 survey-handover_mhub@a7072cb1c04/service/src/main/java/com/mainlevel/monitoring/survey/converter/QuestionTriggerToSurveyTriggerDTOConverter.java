/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.converter;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.survey.trigger.SurveyTriggerDTO;
import com.mainlevel.monitoring.survey.database.node.question.QuestionTrigger;

/**
 * Converter converting {@link QuestionTrigger} to {@link SurveyTriggerDTO}.
 */
@Component
public class QuestionTriggerToSurveyTriggerDTOConverter extends AbstractApplicationAwareConverter<QuestionTrigger, SurveyTriggerDTO> {

    @Override
    public SurveyTriggerDTO convert(QuestionTrigger source) {

        if (source != null) {

            Integer targetQuestionIndex = source.getTargetQuestion() != null ? source.getTargetQuestion().getIndex() : null;

            SurveyTriggerDTO result =
                SurveyTriggerDTO.builder().graphId(source.getGid()).index(source.getIndex()).optionIndex(source.getOptionIndex())
                    .targetQuestionIndex(targetQuestionIndex).compareType(source.getCompareType()).actionType(source.getActionType()).build();

            return result;
        }

        return null;
    }

}
