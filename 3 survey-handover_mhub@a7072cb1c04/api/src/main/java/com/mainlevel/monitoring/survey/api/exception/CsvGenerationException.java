package com.mainlevel.monitoring.survey.api.exception;

/**
 * Exception occurred while creating CSV.
 */
public class CsvGenerationException extends RuntimeException {

    private static final long serialVersionUID = -1267523117182788334L;

    /**
     * Constructor for CsvGenerationException.
     *
     * @param message the error message.
     * @param cause The cause for this exception.
     */
    public CsvGenerationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
