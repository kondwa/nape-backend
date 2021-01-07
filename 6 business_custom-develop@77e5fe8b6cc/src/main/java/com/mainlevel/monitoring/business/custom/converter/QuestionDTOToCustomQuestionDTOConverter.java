/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.NumberUtils;

import com.mainlevel.monitoring.admin.api.dto.template.QuestionDTO;
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
import com.mainlevel.monitoring.survey.api.dto.QuestionType;
import com.mainlevel.monitoring.survey.api.dto.survey.question.AnswerOptionType;
import com.mainlevel.monitoring.survey.api.dto.survey.question.SurveyQuestionDTO;

/**
 * Converter converting {@link SurveyQuestionDTO} to {@link CustomQuestionDTO}.
 */
@Component
public class QuestionDTOToCustomQuestionDTOConverter extends AbstractApplicationAwareConverter<QuestionDTO, CustomQuestionDTO> {

    @Override
    public CustomQuestionDTO convert(QuestionDTO source) {

        Integer index = this.parseNumber(source.getName(), 6);
        QuestionType type = QuestionType.byName(source.getType());

        List<CustomTriggerDTO> triggers = super.getCollectionConversionService().convert(source.getTriggers(), CustomTriggerDTO.class);
        List<CustomValidatorDTO> validators = Collections.emptyList();

        CustomQuestionDTO question;

        switch (type) {

            case COMMENT:
            case TEXT:
                question = createTextQuestion(source);
                break;

            case DATE:
                question = createDateQuestion(source);
                break;

            case NUMBER:
                question = creteNumberQuestion(source);
                break;

            case RADIO:
            case CHECKBOX:
            case DROPDOWN:
            case PREDEFINED_LIST:
                question = creteSelectionQuestion(source);
                break;

            case MATRIX_SINGLE_CHOICE:
            case MATRIX_MULTIPLE_CHOICE:
                question = createMatrixQuestion(source);
                break;

            default:
                throw new IllegalStateException("The question type '" + source.getType() + "' is currently not supported.");
        }

        question.setName(source.getTitle());
        question.setIndex(index);
        question.setQuestionType(type);
        question.setDescription(source.getDescription());
        question.setMandatory(source.getIsRequired());
        question.setVisible(source.getVisible());
        question.setTriggers(triggers);
        question.setValidators(validators);

        return question;
    }

    private CustomNumberQuestionDTO creteNumberQuestion(QuestionDTO source) {
        return CustomNumberQuestionDTO.builder().suffix(source.getSuffix()).build();
    }

    private CustomDateQuestionDTO createDateQuestion(QuestionDTO source) {
        return CustomDateQuestionDTO.builder().build();
    }

    private CustomTextQuestionDTO createTextQuestion(QuestionDTO source) {
        return CustomTextQuestionDTO.builder().build();
    }

    private CustomSelectionQuestionDTO creteSelectionQuestion(QuestionDTO source) {
        List<CustomOptionDTO> options = createOptions(source);

        return CustomSelectionQuestionDTO.builder().options(options).build();
    }

    private CustomMatrixQuestionDTO createMatrixQuestion(QuestionDTO source) {
        List<CustomOptionDTO> options = createOptions(source);

        List<CustomMatrixRowDTO> rows = new ArrayList<>();
        source.getRows().forEach(row -> {
            Integer index = parseNumber(row.getName(), 7);
            CustomMatrixRowDTO resultRow = CustomMatrixRowDTO.builder().name(row.getName()).description(row.getText()).index(index).build();

            rows.add(resultRow);
        });

        return CustomMatrixQuestionDTO.builder().options(options).rows(rows).build();
    }

    private List<CustomOptionDTO> createOptions(QuestionDTO source) {
        List<CustomOptionDTO> options = new ArrayList<>();

        source.getChoices().forEach(choice -> {

            Integer index = this.parseNumber(choice.getName(), 7);

            CustomOptionDTO option = CustomOptionDTO.builder().name(choice.getText()).type(AnswerOptionType.SELECTION).description(choice.getInfoText())
                .index(index).build();
            options.add(option);
        });

        if (Boolean.TRUE.equals(source.getHasOther())) {
            CustomOptionDTO otherOption =
                CustomOptionDTO.builder().name("Other").type(AnswerOptionType.TEXT).description("Other Option").index(options.size()).build();
            options.add(otherOption);
        }

        return options;
    }

    private Integer parseNumber(String name, int index) {
        if (name == null || name.isEmpty()) {
            return null;
        }

        String number = name.substring(index);
        return NumberUtils.parseNumber(number, Integer.class);
    }

}
