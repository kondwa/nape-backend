package com.mainlevel.monitoring.survey.api.exception;

/**
 * Raised when a lock on a reporting period cannot be extended.
 */
public class UnableToExtendLockException extends RuntimeException {

    private static final long serialVersionUID = 2318816282190038137L;

    /**
     * Constructor for UnableToExtendLockException.
     *
     * @param id the reporting period id
     */
    public UnableToExtendLockException(Long id) {
        super(String.format("It's not possible to extend lock on the node [id=%s].", id));
    }
}
