package com.mainlevel.monitoring.survey.api.exception;

/**
 * One reporting period has two or more active sessions.
 */
public class DuplicateParticipantSessionException extends RuntimeException {

    private static final long serialVersionUID = -1267524117182788334L;

    /**
     * Constructor for DuplicateParticipantSessionException.
     *
     * @param message the error message
     */
    public DuplicateParticipantSessionException(String message) {
        super(message);
    }
}
