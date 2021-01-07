package com.mainlevel.monitoring.admin.api.exception;

/**
 * Exception used when a survey template is not found .
 */
public class SurveyNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -2412849993665974246L;
    private static final String DEFAULT_MESSAGE = "Survey (id=%s) doesn't exist in db.";

    /**
     * Constructor for SurveyNotFoundException.
     *
     * @param surveyId the survey template id
     */
    public SurveyNotFoundException(String surveyId) {
        this(DEFAULT_MESSAGE, surveyId);
    }

    /**
     * Constructor for SurveyNotFoundException.
     *
     * @param message the error message
     * @param surveyId the survey template id
     */
    public SurveyNotFoundException(String message, String surveyId) {
        super(String.format(message, surveyId));
    }
}
