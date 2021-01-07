package com.mainlevel.monitoring.admin.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception used when no Indicator was found in Database.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class IndicatorNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 6509808887565932025L;

    /**
     * Constructor for IndicatorNotFoundException.
     *
     * @param message the error message
     */
    public IndicatorNotFoundException(String message) {
        super(message);
    }
}
