/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.participantSession.ParticipantSessionQuestionDTO;
import com.mainlevel.monitoring.survey.database.node.AnswerOption;
import com.mainlevel.monitoring.survey.database.node.question.DateQuestion;
import com.mainlevel.monitoring.survey.database.node.question.MatrixQuestion;
import com.mainlevel.monitoring.survey.database.node.question.NumberQuestion;
import com.mainlevel.monitoring.survey.database.node.question.Question;
import com.mainlevel.monitoring.survey.database.node.question.SelectableQuestion;
import com.mainlevel.monitoring.survey.database.node.question.TextQuestion;

/**
 * Converter converting {@link ParticipantSessionQuestionDTO} to {@link Question}.
 */
@Component
public class ParticipantSessionQuestionDTOToQuestionConverter extends AbstractApplicationAwareConverter<ParticipantSessionQuestionDTO, Question> {

    @Override
    public Question convert(ParticipantSessionQuestionDTO source) {

        Question result;

        switch (source.getType()) {

            case CHECKBOX:
            case DROPDOWN:
            case RADIO: {
                SelectableQuestion question = new SelectableQuestion();
                List<AnswerOption> options = super.getCollectionConversionService().convert(source.getOptions(), AnswerOption.class);
                question.setOptions(options);
                result = question;
                break;
            }

            case NUMBER: {
                NumberQuestion question = new NumberQuestion();
                result = question;
                break;
            }

            case DATE: {
                DateQuestion question = new DateQuestion();
                result = question;
                break;
            }

            case COMMENT:
            case TEXT: {
                TextQuestion question = new TextQuestion();
                result = question;
                break;
            }

            case MATRIX_SINGLE_CHOICE:
            case MATRIX_MULTIPLE_CHOICE: {
                MatrixQuestion question = new MatrixQuestion();
                List<AnswerOption> options = super.getCollectionConversionService().convert(source.getOptions(), AnswerOption.class);
                question.setOptions(options);
                question.setRows(new ArrayList<>());

                result = question;
                break;
            }

            default: {
                throw new IllegalStateException("Question type " + source.getType() + " is not supported yet.");
            }

        }

        // TODO Indicators

        result.setGid(source.getGraphId());
        result.setName(source.getName());
        result.setTitle(source.getTitle());
        result.setType(source.getType());
        result.setIndex(source.getIndex());
        result.setDescription(source.getDescription());
        result.setMandatory(source.getMandatory());
        result.setVisible(source.getVisible());

        return result;
    }

}
