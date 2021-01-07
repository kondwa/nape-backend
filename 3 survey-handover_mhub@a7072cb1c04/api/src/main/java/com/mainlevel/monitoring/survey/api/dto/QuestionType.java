/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.api.dto;

/**
 * Type of survey questions.
 */
public enum QuestionType {

    /**
     * Free text input questions.
     */
    TEXT("text", true, false),

    /**
     * Comments.
     */
    COMMENT("comment", true, false),

    /**
     * Multiple choice checkbox questions.
     */
    CHECKBOX("checkbox", true, true),

    /**
     * Single choice radio buttons.
     */
    RADIO("radio", true, true),

    /**
     * Single choice drop-down.
     */
    DROPDOWN("dropdown", true, true),

    /**
     * Single choice drop-down with customizable selections.
     */
    PREDEFINED_LIST("predefinedList", true, true),

    /**
     * Number input question.
     */
    NUMBER("number", true, false),

    /**
     * Date input question.
     */
    DATE("date", true, false),

    /**
     * Matrix question with single choice.
     */
    MATRIX_SINGLE_CHOICE("matrixSingleChoice", false, true),

    /**
     * Matrix question with multiple choice.
     */
    MATRIX_MULTIPLE_CHOICE("matrixMultipleChoice", false, true);

    /**
     * Constructor for QuestionType.
     *
     * @param name name of the question
     * @param simpleType true if the type is simple, false if it is complex
     * @param selectable true if the type is selectable, false if not
     */
    private QuestionType(String name, boolean simpleType, boolean selectable) {
        this.name = name;
        this.simple = simpleType;
        this.selectable = selectable;
    }

    private String name;

    private boolean simple;

    private boolean selectable;

    /**
     * Gets the simple for QuestionType.
     *
     * @return true if the type is simple (like text, number, radio, ...) or false if complex (table, matrix, ...).
     */
    public boolean isSimple() {
        return simple;
    }

    /**
     * Gets the selectable for QuestionType.
     *
     * @return true if the type is selectable (like checkbox, radio, ...) or false if not (text, numeric, ...).
     */
    public boolean isSelectable() {
        return selectable;
    }

    /**
     * Gets the name for QuestionType.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Find the enum literal by its name.
     *
     * @param name the name of the literal
     * @return the enum literal, or null if not valid
     */
    public static QuestionType byName(String name) {
        for (QuestionType type : values()) {
            if (type.getName().equalsIgnoreCase(name)) {
                return type;
            }
        }

        throw new IllegalArgumentException("Question type '" + name + "' is not supported.");
    }

}
