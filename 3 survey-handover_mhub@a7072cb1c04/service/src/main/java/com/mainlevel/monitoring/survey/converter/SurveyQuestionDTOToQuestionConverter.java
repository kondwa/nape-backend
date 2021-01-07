/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.survey.question.SurveyDateQuestionDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.question.SurveyMatrixQuestionDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.question.SurveyNumberQuestionDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.question.SurveyQuestionDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.question.SurveySelectionQuestionDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.question.SurveyTextQuestionDTO;
import com.mainlevel.monitoring.survey.database.node.AnswerOption;
import com.mainlevel.monitoring.survey.database.node.question.DateQuestion;
import com.mainlevel.monitoring.survey.database.node.question.MatrixQuestion;
import com.mainlevel.monitoring.survey.database.node.question.MatrixQuestionRow;
import com.mainlevel.monitoring.survey.database.node.question.NumberQuestion;
import com.mainlevel.monitoring.survey.database.node.question.Question;
import com.mainlevel.monitoring.survey.database.node.question.QuestionTrigger;
import com.mainlevel.monitoring.survey.database.node.question.SelectableQuestion;
import com.mainlevel.monitoring.survey.database.node.question.TextQuestion;

/**
 * Converter converting {@link Question} to {@link SurveyQuestionDTO}.
 */
@Component
public class SurveyQuestionDTOToQuestionConverter extends AbstractApplicationAwareConverter<SurveyQuestionDTO, Question> {

    @Override
    public Question convert(SurveyQuestionDTO source) {

        Question result;

        switch (source.getType()) {

            case CHECKBOX:
            case DROPDOWN:
            case RADIO: {
                SurveySelectionQuestionDTO sourceQuestion = (SurveySelectionQuestionDTO) source;
                SelectableQuestion question = new SelectableQuestion();
                List<AnswerOption> options = super.getCollectionConversionService().convert(sourceQuestion.getOptions(), AnswerOption.class);
                question.setOptions(options);
                result = question;
                break;
            }

            case NUMBER: {
                SurveyNumberQuestionDTO sourceQuestion = (SurveyNumberQuestionDTO) source;
                NumberQuestion question = new NumberQuestion();
                question.setSuffix(sourceQuestion.getSuffix());
                question.setPattern(sourceQuestion.getPattern());
                result = question;
                break;
            }

            case DATE: {
                SurveyDateQuestionDTO sourceQuestion = (SurveyDateQuestionDTO) source;
                DateQuestion question = new DateQuestion();
                question.setPattern(sourceQuestion.getPattern());
                result = question;
                break;
            }

            case COMMENT:
            case TEXT: {
                SurveyTextQuestionDTO sourceQuestion = (SurveyTextQuestionDTO) source;
                TextQuestion question = new TextQuestion();
                question.setPattern(sourceQuestion.getPattern());
                question.setMinChars(sourceQuestion.getMinChars());
                question.setMaxChars(sourceQuestion.getMaxChars());
                result = question;
                break;
            }

            case MATRIX_SINGLE_CHOICE:
            case MATRIX_MULTIPLE_CHOICE: {
                SurveyMatrixQuestionDTO sourceQuestion = (SurveyMatrixQuestionDTO) source;
                MatrixQuestion question = new MatrixQuestion();

                List<AnswerOption> options = super.getCollectionConversionService().convert(sourceQuestion.getOptions(), AnswerOption.class);
                question.setOptions(options);
                question.setRows(new ArrayList<>());

                sourceQuestion.getRows().forEach(row -> {
                    question.getRows().add(MatrixQuestionRow.builder().gid(row.getGraphId()).index(row.getIndex()).name(row.getName())
                        .description(row.getDescription()).build());
                });

                result = question;
                break;
            }

            default: {
                throw new IllegalStateException("Question type " + source.getType() + " is not supported yet.");
            }

        }

        List<QuestionTrigger> triggers = super.getCollectionConversionService().convert(source.getTriggers(), QuestionTrigger.class);

        // TODO Indicators / Validators

        result.setGid(source.getGraphId());
        result.setName(source.getName());
        result.setTitle(source.getTitle());
        result.setIndex(source.getIndex());
        result.setType(source.getType());
        result.setDescription(source.getDescription());
        result.setMandatory(source.getMandatory());
        result.setTriggers(triggers);
        result.setVisible(source.getVisible());

        return result;
    }

}
