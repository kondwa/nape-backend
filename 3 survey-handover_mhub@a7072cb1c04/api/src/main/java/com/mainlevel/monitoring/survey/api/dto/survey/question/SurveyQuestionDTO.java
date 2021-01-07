/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.api.dto.survey.question;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.mainlevel.monitoring.survey.api.dto.QuestionType;
import com.mainlevel.monitoring.survey.api.dto.survey.trigger.SurveyTriggerDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO holding a survey question.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({@JsonSubTypes.Type(value = SurveyDateQuestionDTO.class, name = "SurveyDateQuestionDTO"),
    @JsonSubTypes.Type(value = SurveyMatrixQuestionDTO.class, name = "SurveyMatrixQuestionDTO"),
    @JsonSubTypes.Type(value = SurveyNumberQuestionDTO.class, name = "SurveyNumberQuestionDTO"),
    @JsonSubTypes.Type(value = SurveySelectionQuestionDTO.class, name = "SurveySelectionQuestionDTO"),
    @JsonSubTypes.Type(value = SurveyTextQuestionDTO.class, name = "SurveyTextQuestionDTO")})
public abstract class SurveyQuestionDTO {

    private Long graphId;

    private String name;

    private Integer index;

    private String title;

    private String description;

    private Boolean mandatory;

    private Boolean visible;

    private QuestionType type;

    private List<SurveyQuestionAnswerDTO> answers;

    private List<SurveyTriggerDTO> triggers;

}
