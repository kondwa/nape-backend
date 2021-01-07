package com.mainlevel.monitoring.survey.api.exception;

/**
 * Determinate a try of an illegal modification of survey results.
 */
public class SurveyIllegalModificationException extends RuntimeException {

    private static final String MESSAGE = "Modification of survey in status [%s] is not allowed.";
    private static final long serialVersionUID = 5605166708439627703L;

    /**
     * Constructor for SurveyIllegalModificationException.
     *
     * @param surveyStatus status of the survey
     */
    public SurveyIllegalModificationException(String surveyStatus) {
        super(String.format(MESSAGE, surveyStatus));
    }

}
