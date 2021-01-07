/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.api.dto.survey.trigger;

/**
 * Comparison types for triggers.
 */
public enum SurveyTriggerCompareType {

    /**
     * The selected answer must be equal.
     */
    EQUALS("EQ"),
    
    /**
     * The selected answer must be equal or greater.
     */
    GREATEREQUALS("GE"),
    
    /**
     * The selected answer must be greater.
     */
    GREATERTHAN("GT"),

    /**
     * The selected answer must be unequal.
     */
    NOT_EQUALS("NOT_EQ");

    private SurveyTriggerCompareType(String name) {
        this.name = name;
    }

    private String name;

    /**
     * Gets the name for SurveyTriggerCompareType.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Retrive the enum constant based on its name.
     *
     * @param name the compare name
     * @return the enum literal or null
     */
    public static SurveyTriggerCompareType valueOfName(String name) {
        for (SurveyTriggerCompareType value : SurveyTriggerCompareType.values()) {
            if (value.getName().equalsIgnoreCase(name)) {
                return value;
            }
        }
        return null;
    }

}
