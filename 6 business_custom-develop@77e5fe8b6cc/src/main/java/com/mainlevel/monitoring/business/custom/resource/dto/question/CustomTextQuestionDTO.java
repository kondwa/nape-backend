/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.resource.dto.question;

import java.util.List;

import com.mainlevel.monitoring.business.custom.resource.dto.answer.CustomQuestionAnswerDTO;
import com.mainlevel.monitoring.survey.api.dto.QuestionType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO for a text question of a template.
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class CustomTextQuestionDTO extends CustomQuestionDTO {

    @ApiModelProperty("Pattern for the text field.")
    private String pattern;

    @ApiModelProperty("Minimum amount of characters.")
    private Integer minChars;

    @ApiModelProperty("Maximum amount of characters.")
    private Integer maxChars;

    /**
     * Constructor for CustomDateQuestionDTO.
     *
     * @param graphId id of the question
     * @param name name of the question
     * @param title of the question
     * @param description description of the question
     * @param index index of the question
     * @param required required flag
     * @param visible visibla flag
     * @param questionType question type
     * @param answers list of answers
     * @param triggers list of triggers
     * @param validators list of validators
     * @param pattern pattern of the text field
     * @param minChars minimum amount of characters
     * @param maxChars maximum amount of characters
     */
    @Builder
    public CustomTextQuestionDTO(Long graphId, String name, String title, String description, Integer index, Boolean required, Boolean visible,
        QuestionType questionType, List<CustomQuestionAnswerDTO> answers, List<CustomTriggerDTO> triggers, List<CustomValidatorDTO> validators,
        String pattern, Integer minChars, Integer maxChars) {
        super(graphId, name, title, description, index, required, visible, questionType, answers, triggers, validators);

        this.pattern = pattern;
        this.minChars = minChars;
        this.maxChars = maxChars;
    }

}
