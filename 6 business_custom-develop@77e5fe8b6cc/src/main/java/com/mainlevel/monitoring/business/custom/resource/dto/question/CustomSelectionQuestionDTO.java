/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.resource.dto.question;

import java.util.List;

import com.mainlevel.monitoring.business.custom.resource.dto.answer.CustomQuestionAnswerDTO;
import com.mainlevel.monitoring.survey.api.dto.QuestionType;
import com.mainlevel.monitoring.survey.api.dto.survey.question.SurveySelectionType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO for a choice question of a template.
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class CustomSelectionQuestionDTO extends CustomQuestionDTO {

    @ApiModelProperty("Type of selection.")
    private SurveySelectionType selectionType;

    @ApiModelProperty("List of options.")
    private List<CustomOptionDTO> options;

    /**
     * Constructor for CustomSelectionQuestionDTO.
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
     * @param selectionType type of selection
     * @param options options of this choice question
     */
    @Builder
    public CustomSelectionQuestionDTO(Long graphId, String name, String title, String description, Integer index, Boolean required, Boolean visible,
        QuestionType questionType, List<CustomQuestionAnswerDTO> answers, List<CustomTriggerDTO> triggers, List<CustomValidatorDTO> validators,
        SurveySelectionType selectionType, List<CustomOptionDTO> options) {
        super(graphId, name, title, description, index, required, visible, questionType, answers, triggers, validators);

        this.selectionType = selectionType;
        this.options = options;
    }

}
