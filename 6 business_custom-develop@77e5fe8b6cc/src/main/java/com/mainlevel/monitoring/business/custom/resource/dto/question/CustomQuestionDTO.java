/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.resource.dto.question;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.mainlevel.monitoring.business.custom.resource.dto.answer.CustomQuestionAnswerDTO;
import com.mainlevel.monitoring.survey.api.dto.QuestionType;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO holding information about a template question.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({@JsonSubTypes.Type(value = CustomDateQuestionDTO.class, name = "DATE"),
    @JsonSubTypes.Type(value = CustomMatrixQuestionDTO.class, name = "MATRIX"),
    @JsonSubTypes.Type(value = CustomNumberQuestionDTO.class, name = "NUMBER"),
    @JsonSubTypes.Type(value = CustomSelectionQuestionDTO.class, name = "SELECTION"),
    @JsonSubTypes.Type(value = CustomTextQuestionDTO.class, name = "TEXT")})
public abstract class CustomQuestionDTO {

    @ApiModelProperty("Graph ID of the question.")
    private Long graphId;

    @ApiModelProperty("Name of the question.")
    private String name;

    @ApiModelProperty("Title of the question.")
    private String title;

    @ApiModelProperty("Description of the question.")
    private String description;

    @ApiModelProperty("Index of the question within the template.")
    private Integer index;

    @ApiModelProperty("Question is required.")
    private Boolean mandatory;

    @ApiModelProperty("Question is visible.")
    private Boolean visible;

    @ApiModelProperty("Type of the question.")
    private QuestionType questionType;

    @ApiModelProperty("Type of the question.")
    private List<CustomQuestionAnswerDTO> answers;

    @ApiModelProperty("Triggers of the question.")
    private List<CustomTriggerDTO> triggers;

    @ApiModelProperty("Validators of the question.")
    private List<CustomValidatorDTO> validators;

}
