package com.mainlevel.monitoring.survey.api.exception;

import com.mainlevel.monitoring.survey.api.dto.survey.ReportingPeriodStatus;

/**
 * Determinate a try of set wrong status in the survey workflow.
 */
public class WrongWorkflowStatusException extends RuntimeException {

    private static final long serialVersionUID = 4413902067058123711L;

    private static final String MESSAGE = "Changing status [%s] to status [%s] is not allowed.";

    /**
     * Constructor for WrongWorkflowStatusException.
     *
     * @param oldStatus the old status
     * @param newStatus the new status
     */
    public WrongWorkflowStatusException(ReportingPeriodStatus oldStatus, ReportingPeriodStatus newStatus) {
        super(String.format(MESSAGE, oldStatus, newStatus));
    }

}
