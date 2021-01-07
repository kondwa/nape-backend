/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.api.dto.survey.answer;

/**
 * Type of answer.
 */
public enum SurveyAnswerType {

    /**
     * Answer relates to a selectable answer option.
     */
    OPTION,

    /**
     * Answer holds a single value.
     */
    VALUE,

    /**
     * Answer holds both a selectable option and additional text value.
     */
    BOTH

}
