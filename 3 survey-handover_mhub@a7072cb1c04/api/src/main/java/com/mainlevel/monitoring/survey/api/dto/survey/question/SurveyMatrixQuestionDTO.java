/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.api.dto.survey.question;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO for a matrix question.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class SurveyMatrixQuestionDTO extends SurveyQuestionDTO {

    private SurveySelectionType selectionType;

    private List<AnswerOptionDTO> options;

    private List<SurveyMatrixRowDTO> rows;

}
