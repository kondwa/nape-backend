package com.mainlevel.monitoring.admin.api.exception;

/**
 * Exception used when we try update an entity by code, and code is null.
 */
public class SaveByNullException extends RuntimeException {

    private static final long serialVersionUID = -3579145558399436569L;

    private static final String MESSAGE = "It isn't possible to save entity %s by the property %s with the null value.";

    /**
     * Constructor for SaveByNullException.
     *
     * @param entityClass the entity class
     * @param propertyName the field name
     */
    public SaveByNullException(Class<?> entityClass, String propertyName) {
        super(String.format(MESSAGE, entityClass.getName(), propertyName));
    }

}
