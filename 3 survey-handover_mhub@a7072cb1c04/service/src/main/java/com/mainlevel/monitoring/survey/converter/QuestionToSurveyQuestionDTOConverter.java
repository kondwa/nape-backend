/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.QuestionType;
import com.mainlevel.monitoring.survey.api.dto.survey.question.AnswerOptionDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.question.SurveyDateQuestionDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.question.SurveyMatrixQuestionDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.question.SurveyMatrixRowDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.question.SurveyNumberQuestionDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.question.SurveyQuestionDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.question.SurveySelectionQuestionDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.question.SurveySelectionType;
import com.mainlevel.monitoring.survey.api.dto.survey.question.SurveyTextQuestionDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.trigger.SurveyTriggerDTO;
import com.mainlevel.monitoring.survey.database.node.question.DateQuestion;
import com.mainlevel.monitoring.survey.database.node.question.MatrixQuestion;
import com.mainlevel.monitoring.survey.database.node.question.NumberQuestion;
import com.mainlevel.monitoring.survey.database.node.question.Question;
import com.mainlevel.monitoring.survey.database.node.question.SelectableQuestion;
import com.mainlevel.monitoring.survey.database.node.question.TextQuestion;

/**
 * Converter converting {@link Question} to {@link SurveyQuestionDTO}.
 */
@Component
public class QuestionToSurveyQuestionDTOConverter extends AbstractApplicationAwareConverter<Question, SurveyQuestionDTO> {

    @Override
    public SurveyQuestionDTO convert(Question source) {

        SurveyQuestionDTO result;

        switch (source.getType()) {

            case CHECKBOX:
            case DROPDOWN:
            case RADIO: {
                SelectableQuestion sourceQuestion = (SelectableQuestion) source;
                SurveySelectionQuestionDTO question = new SurveySelectionQuestionDTO();
                List<AnswerOptionDTO> options = super.getCollectionConversionService().convert(sourceQuestion.getOptions(), AnswerOptionDTO.class);
                question.setOptions(options);
                question.setSelectionType(source.getType() == QuestionType.CHECKBOX ? SurveySelectionType.MULTIPLE : SurveySelectionType.SINGLE);
                result = question;
                break;
            }

            case NUMBER: {
                NumberQuestion sourceQuestion = (NumberQuestion) source;
                SurveyNumberQuestionDTO question = new SurveyNumberQuestionDTO();
                question.setSuffix(sourceQuestion.getSuffix());
                question.setPattern(sourceQuestion.getPattern());
                result = question;
                break;
            }

            case DATE: {
                DateQuestion sourceQuestion = (DateQuestion) source;
                SurveyDateQuestionDTO question = new SurveyDateQuestionDTO();
                question.setPattern(sourceQuestion.getPattern());
                result = question;
                break;
            }

            case COMMENT:
            case TEXT: {
                TextQuestion sourceQuestion = (TextQuestion) source;
                SurveyTextQuestionDTO question = new SurveyTextQuestionDTO();
                question.setPattern(sourceQuestion.getPattern());
                question.setMinChars(sourceQuestion.getMinChars());
                question.setMaxChars(sourceQuestion.getMaxChars());
                result = question;
                break;
            }

            case MATRIX_SINGLE_CHOICE:
            case MATRIX_MULTIPLE_CHOICE: {
                MatrixQuestion sourceQuestion = (MatrixQuestion) source;
                SurveyMatrixQuestionDTO question = new SurveyMatrixQuestionDTO();

                List<AnswerOptionDTO> options = super.getCollectionConversionService().convert(sourceQuestion.getOptions(), AnswerOptionDTO.class);
                question.setOptions(options);
                question.setRows(new ArrayList<>());

                if (sourceQuestion.getRows() != null) {
                    sourceQuestion.getRows().forEach(row -> {
                        question.getRows().add(SurveyMatrixRowDTO.builder().graphId(row.getGid()).index(row.getIndex()).name(row.getName())
                            .description(row.getDescription()).build());
                    });
                }

                result = question;
                break;
            }

            default: {
                throw new IllegalStateException("Question type " + source.getType() + " is not supported yet.");
            }

        }

        // TODO Indicators

        result.setGraphId(source.getGid());
        result.setName(source.getName());
        result.setTitle(source.getTitle());
        result.setIndex(source.getIndex());
        result.setType(source.getType());
        result.setDescription(source.getDescription());
        result.setMandatory(source.getMandatory());
        result.setVisible(source.getVisible());

        List<SurveyTriggerDTO> triggers = super.getCollectionConversionService().convert(source.getTriggers(), SurveyTriggerDTO.class);
        result.setTriggers(triggers);

        return result;
    }

}
