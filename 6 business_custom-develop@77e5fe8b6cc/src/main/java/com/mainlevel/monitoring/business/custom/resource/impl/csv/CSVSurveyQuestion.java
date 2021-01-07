/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.resource.impl.csv;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Represents a single question.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class CSVSurveyQuestion {

    private static final String SINGLE_ANSWER = "single";

    private String name;

    private boolean singleValue;

    @Builder.Default
    private Map<String, String> answers = new HashMap<>();

    /**
     * Getter for the single answer in case of a single value question.
     *
     * @return the single answer
     */
    public String getSingleAnswer() {
        return answers.get(SINGLE_ANSWER);
    }

    /**
     * Set the single answer into the answer map.
     *
     * @param answer the answer to set
     */
    public void setSingleAnswer(String answer) {
        answers.put(SINGLE_ANSWER, answer);
    }

}
