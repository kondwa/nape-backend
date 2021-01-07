/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.converter;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.survey.trigger.SurveyTriggerDTO;
import com.mainlevel.monitoring.survey.database.node.question.QuestionTrigger;

/**
 * Converter converting {@link SurveyTriggerDTO} to {@link QuestionTrigger}.
 */
@Component
public class SurveyTriggerDTOToQuestionTriggerConverter extends AbstractApplicationAwareConverter<SurveyTriggerDTO, QuestionTrigger> {

    @Override
    public QuestionTrigger convert(SurveyTriggerDTO source) {

        QuestionTrigger result = null;

        if (source != null) {

            result = QuestionTrigger.builder().gid(source.getGraphId()).index(source.getIndex()).actionType(source.getActionType())
                .compareType(source.getCompareType()).optionIndex(source.getOptionIndex()).targetQuestionIndex(source.getTargetQuestionIndex())
                .build();
        }

        return result;
    }

}
