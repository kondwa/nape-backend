/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.converter;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.participantSession.ParticipantSessionQuestionDTO;
import com.mainlevel.monitoring.survey.database.node.question.Question;

/**
 * Converter converting {@link Question} to {@link ParticipantSessionQuestionDTO}.
 */
@Component
public class QuestionToParticipantSessionQuestionDTOConverter extends AbstractApplicationAwareConverter<Question, ParticipantSessionQuestionDTO> {

    @Override
    public ParticipantSessionQuestionDTO convert(Question source) {

        ParticipantSessionQuestionDTO result =
            ParticipantSessionQuestionDTO.builder().graphId(source.getGid()).name(source.getName()).description(source.getDescription())
                .index(source.getIndex()).mandatory(source.getMandatory()).visible(source.getVisible()).title(source.getTitle()).build();

        return result;
    }

}
