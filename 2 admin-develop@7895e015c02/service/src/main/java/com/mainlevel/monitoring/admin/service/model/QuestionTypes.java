package com.mainlevel.monitoring.admin.service.model;

/**
 * Interface defining the possible QuestionTypes.
 */
public interface QuestionTypes {

    /**
     * Constant for text field questions.
     */
    String TEXT = "text";

    /**
     * Constant for comment questions.
     */
    String COMMENT = "comment";

    /**
     * Constant for checkbox questions.
     */
    String CHECKBOX = "checkbox";

    /**
     * Constant for radio button questions.
     */
    String RADIO = "radio";

    /**
     * Constant for drop-down questions.
     */
    String DROPDOWN = "dropdown";

    /**
     * Constant for predefined list questions.
     */
    String PREDEFINED_LIST = "predefinedList";

    /**
     * Constant for number field questions.
     */
    String NUMBER = "number";

    /**
     * Constant for date field questions.
     */
    String DATE = "date";

    /**
     * Constant for matrix questions with single choice.
     */
    String MATRIX_SINGLE_CHOICE = "matrixSingleChoice";

    /**
     * Constant for matrix questions with multiple choice.
     */
    String MATRIX_MULTIPLE_CHOICE = "matrixMultipleChoice";

    /**
     * Constant for table questions with fixed amount of rows.
     */
    String STATIC_TABLE = "staticTable";

    /**
     * Constant for table questions with dynamic amount of rows.
     */
    String DYNAMIC_TABLE = "dynamicTable";

    /**
     * Constant for project picking questions.
     */
    String PROJECT_DATA = "projectData";

}
