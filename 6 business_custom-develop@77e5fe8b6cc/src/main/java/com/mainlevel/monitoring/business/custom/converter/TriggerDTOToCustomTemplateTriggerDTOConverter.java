/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.converter;

import org.springframework.stereotype.Component;
import org.springframework.util.NumberUtils;

import com.mainlevel.monitoring.admin.api.dto.template.TriggerDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.question.CustomTriggerDTO;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.survey.trigger.SurveyTriggerCompareType;
import com.mainlevel.monitoring.survey.api.dto.survey.trigger.SurveyTriggerType;

/**
 * Converter converting {@link TriggerDTO} to {@link CustomTriggerDTO}.
 */
@Component
public class TriggerDTOToCustomTemplateTriggerDTOConverter extends AbstractApplicationAwareConverter<TriggerDTO, CustomTriggerDTO> {

    @Override
    public CustomTriggerDTO convert(TriggerDTO source) {

        Integer targetIndex = parseNumber(source.getTargetName(), 6);
        Integer optionIndex = parseNumber(source.getAnswerValue(), 7);

        SurveyTriggerType actionType = SurveyTriggerType.valueOf(source.getAction());
        SurveyTriggerCompareType compareType = SurveyTriggerCompareType.valueOfName(source.getCompare());

        CustomTriggerDTO result = CustomTriggerDTO.builder().index(source.getId()).optionIndex(optionIndex)
            .targetQuestionIndex(targetIndex).actionType(actionType).compareType(compareType).build();

        return result;
    }

    private Integer parseNumber(String name, int index) {
        if (name == null || name.isEmpty()) {
            return null;
        }

        String number = name.substring(index);
        return NumberUtils.parseNumber(number, Integer.class);
    }

}
