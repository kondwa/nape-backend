package com.mainlevel.monitoring.survey.api.dto.survey;

/**
 * Enumeration with all reporting period workflow statuses.
 */
public enum ReportingPeriodStatus {

    /**
     * Reporting period has been created an not yet modified.
     */
    NEW,

    /**
     * Reporting period has already been edited.
     */
    IN_PROGRESS,

    /**
     * Work on reporting period is finished.
     */
    SENT,

    /**
     * Reporting period is not valid anymore.
     */
    OBSOLETE,

    /**
     * Survey evaluation is finished.
     */
    CLOSED;
}
