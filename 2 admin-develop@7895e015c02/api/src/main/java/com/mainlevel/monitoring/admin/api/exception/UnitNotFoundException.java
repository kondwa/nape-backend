package com.mainlevel.monitoring.admin.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception used when no FundingArea was found in Database.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UnitNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -4764716132243934186L;

    /**
     * Constructor for FundingAreaNotFoundException.
     *
     * @param message the error message
     */
    public UnitNotFoundException(String message) {
        super(message);
    }

}
