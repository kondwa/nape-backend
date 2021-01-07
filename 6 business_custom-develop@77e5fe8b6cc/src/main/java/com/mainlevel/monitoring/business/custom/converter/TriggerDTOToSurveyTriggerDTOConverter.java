/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.converter;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.admin.api.dto.template.TriggerDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.question.CustomTriggerDTO;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.survey.trigger.SurveyTriggerCompareType;
import com.mainlevel.monitoring.survey.api.dto.survey.trigger.SurveyTriggerDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.trigger.SurveyTriggerType;

/**
 * Converter converting {@link TriggerDTO} to {@link CustomTriggerDTO}.
 */
@Component
public class TriggerDTOToSurveyTriggerDTOConverter extends AbstractApplicationAwareConverter<TriggerDTO, SurveyTriggerDTO> {

    @Override
    public SurveyTriggerDTO convert(TriggerDTO source) {

        SurveyTriggerType actionType = SurveyTriggerType.valueOf(source.getAction());
        SurveyTriggerCompareType compareType = SurveyTriggerCompareType.valueOfName(source.getCompare());

        SurveyTriggerDTO result = SurveyTriggerDTO.builder().index(source.getId()).optionValue(source.getAnswerValue())
            .targetQuestionName(source.getTargetName()).actionType(actionType).compareType(compareType).build();

        return result;
    }

}
