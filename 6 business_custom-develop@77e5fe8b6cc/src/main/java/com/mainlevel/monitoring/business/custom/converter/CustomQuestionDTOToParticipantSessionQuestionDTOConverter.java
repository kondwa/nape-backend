/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.converter;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.business.custom.resource.dto.question.CustomQuestionDTO;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.participantSession.ParticipantSessionQuestionDTO;

/**
 * Converter converting {@link CustomQuestionDTO} to {@link ParticipantSessionQuestionDTO}.
 */
@Component
public class CustomQuestionDTOToParticipantSessionQuestionDTOConverter
    extends AbstractApplicationAwareConverter<CustomQuestionDTO, ParticipantSessionQuestionDTO> {

    @Override
    public ParticipantSessionQuestionDTO convert(CustomQuestionDTO source) {

        ParticipantSessionQuestionDTO result = ParticipantSessionQuestionDTO.builder().graphId(source.getGraphId()).name(source.getName())
            .index(source.getIndex()).title(source.getTitle()).description(source.getDescription()).mandatory(source.getMandatory())
            .visible(source.getVisible()).type(source.getQuestionType()).build();

        return result;
    }

}
