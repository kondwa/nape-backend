/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.converter;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.business.custom.resource.dto.question.CustomTriggerDTO;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.survey.trigger.SurveyTriggerDTO;

/**
 * Converter converting {@link SurveyTriggerDTO} to {@link CustomTriggerDTO}.
 */
@Component
public class SurveyTriggerDTOToCustomTriggerDTOConverter extends AbstractApplicationAwareConverter<SurveyTriggerDTO, CustomTriggerDTO> {

    @Override
    public CustomTriggerDTO convert(SurveyTriggerDTO source) {

        CustomTriggerDTO result = null;
        if (source != null) {
            result = CustomTriggerDTO.builder().graphId(source.getGraphId()).actionType(source.getActionType()).compareType(source.getCompareType())
                .targetQuestionIndex(source.getTargetQuestionIndex()).index(source.getIndex()).optionIndex(source.getOptionIndex()).build();
        }

        return result;

    }

}
