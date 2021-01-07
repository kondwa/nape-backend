/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.business.custom.resource.dto.answer.CustomQuestionAnswerDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.question.CustomDateQuestionDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.question.CustomMatrixQuestionDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.question.CustomMatrixRowDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.question.CustomNumberQuestionDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.question.CustomOptionDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.question.CustomQuestionDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.question.CustomSelectionQuestionDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.question.CustomTextQuestionDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.question.CustomTriggerDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.question.CustomValidatorDTO;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.survey.api.dto.survey.question.AnswerOptionDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.question.SurveyDateQuestionDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.question.SurveyMatrixQuestionDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.question.SurveyNumberQuestionDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.question.SurveyQuestionDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.question.SurveySelectionQuestionDTO;
import com.mainlevel.monitoring.survey.api.dto.survey.question.SurveyTextQuestionDTO;

/**
 * Converter converting {@link SurveyQuestionDTO} to {@link CustomQuestionDTO}.
 */
@Component
public class SurveyQuestionDTOToCustomQuestionDTOConverter extends AbstractApplicationAwareConverter<SurveyQuestionDTO, CustomQuestionDTO> {

    @Override
    public CustomQuestionDTO convert(SurveyQuestionDTO source) {

        CustomQuestionDTO question;

        switch (source.getType()) {

            case COMMENT:
            case TEXT:
                question = createTextQuestion((SurveyTextQuestionDTO) source);
                break;

            case DATE:
                question = createDateQuestion((SurveyDateQuestionDTO) source);
                break;

            case NUMBER:
                question = creteNumberQuestion((SurveyNumberQuestionDTO) source);
                break;

            case RADIO:
            case CHECKBOX:
            case DROPDOWN:
            case PREDEFINED_LIST:
                question = creteSelectionQuestion((SurveySelectionQuestionDTO) source);
                break;

            case MATRIX_SINGLE_CHOICE:
            case MATRIX_MULTIPLE_CHOICE:
                question = createMatrixQuestion((SurveyMatrixQuestionDTO) source);
                break;

            default:
                throw new IllegalStateException("The question type '" + source.getType() + "' is currently not supported.");
        }

        List<CustomQuestionAnswerDTO> answers = super.getCollectionConversionService().convert(source.getAnswers(), CustomQuestionAnswerDTO.class);
        // Map<Long, CustomQuestionAnswerDTO> answerMap = answers.stream().collect(Collectors.toMap(a -> a.getAnswer().getGraphId(), a -> a));

        List<CustomTriggerDTO> triggers = super.getCollectionConversionService().convert(source.getTriggers(), CustomTriggerDTO.class);
        List<CustomValidatorDTO> validators = Collections.emptyList();

        question.setGraphId(source.getGraphId());
        question.setName(source.getName());
        question.setTitle(source.getTitle());
        question.setDescription(source.getDescription());
        question.setIndex(source.getIndex());
        question.setQuestionType(source.getType());
        question.setMandatory(source.getMandatory());
        question.setVisible(source.getVisible());

        question.setAnswers(answers);
        question.setTriggers(triggers);
        question.setValidators(validators);

        return question;
    }

    private CustomNumberQuestionDTO creteNumberQuestion(SurveyNumberQuestionDTO source) {
        return CustomNumberQuestionDTO.builder().suffix(source.getSuffix()).pattern(source.getPattern()).build();
    }

    private CustomDateQuestionDTO createDateQuestion(SurveyDateQuestionDTO source) {
        return CustomDateQuestionDTO.builder().pattern(source.getPattern()).build();
    }

    private CustomTextQuestionDTO createTextQuestion(SurveyTextQuestionDTO source) {
        return CustomTextQuestionDTO.builder().pattern(source.getPattern()).minChars(source.getMinChars()).maxChars(source.getMaxChars()).build();
    }

    private CustomSelectionQuestionDTO creteSelectionQuestion(SurveySelectionQuestionDTO source) {
        List<CustomOptionDTO> options = convertOptions(source.getOptions());

        return CustomSelectionQuestionDTO.builder().options(options).selectionType(source.getSelectionType()).build();
    }

    private CustomMatrixQuestionDTO createMatrixQuestion(SurveyMatrixQuestionDTO source) {

        List<CustomOptionDTO> options = convertOptions(source.getOptions());

        List<CustomMatrixRowDTO> rows = new ArrayList<>();
        source.getRows().forEach(row -> {
            CustomMatrixRowDTO resultRow = CustomMatrixRowDTO.builder().graphId(row.getGraphId()).name(row.getName()).description(row.getDescription())
                .index(row.getIndex()).build();

            rows.add(resultRow);
        });

        return CustomMatrixQuestionDTO.builder().options(options).rows(rows).build();
    }

    private List<CustomOptionDTO> convertOptions(List<AnswerOptionDTO> options) {
        List<CustomOptionDTO> result = super.getCollectionConversionService().convert(options, CustomOptionDTO.class);
        result.sort((CustomOptionDTO o1, CustomOptionDTO o2) -> o1.getIndex() != null ? o1.getIndex().compareTo(o2.getIndex()) : 0);
        return result;
    }

}
