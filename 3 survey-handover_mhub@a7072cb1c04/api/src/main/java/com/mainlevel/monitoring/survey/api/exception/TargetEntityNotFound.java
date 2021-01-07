package com.mainlevel.monitoring.survey.api.exception;

/**
 * Raised when a target entity cannot be found in database.
 */
public class TargetEntityNotFound extends RuntimeException {

    private static final String MESSAGE = "Target entity [%s] with id[%s] is not found.";
    private static final long serialVersionUID = 3430134368616697356L;

    /**
     * Constructor for TargetEntityNotFound.
     *
     * @param clazz the entity class
     * @param id the entity id
     */
    public TargetEntityNotFound(Class<?> clazz, Object id) {
        super(String.format(MESSAGE, clazz.getName(), String.valueOf(id)));
    }
}
