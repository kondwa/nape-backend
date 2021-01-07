package com.mainlevel.monitoring.survey.api.exception;

/**
 * Target reporting period doesn't exist.
 */
@SuppressWarnings("serial")
public class ReportingPeriodNotExistException extends RuntimeException {

    /**
     * Constructor for ReportingPeriodNotExistException.
     *
     * @param message the error message
     */
    public ReportingPeriodNotExistException(final String message) {
        super(message);
    }
}
