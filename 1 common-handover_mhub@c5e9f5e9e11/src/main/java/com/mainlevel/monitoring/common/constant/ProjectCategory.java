package com.mainlevel.monitoring.common.constant;

/**
 * All possible categories of projects.
 */
public enum ProjectCategory {

    /** Individual project. */
    INDIVIDUAL("IND"),

    /** SAP project. */
    SAP("SAP");

    private String value;

    /**
     * Constructor for ProjectCategory.
     *
     * @param value the category value
     */
    private ProjectCategory(final String value) {
        this.value = value;
    }

    /**
     * Retrieve the enum for the given value.
     *
     * @param value the value
     * @return the enum
     */
    public static ProjectCategory getEnumByValue(final String value) {
        ProjectCategory result = null;
        if (value != null) {
            if (value.equalsIgnoreCase(INDIVIDUAL.value)) {
                result = INDIVIDUAL;
            } else if (value.equalsIgnoreCase(SAP.value)) {
                result = SAP;
            } else {
                throw new IllegalArgumentException("Enumeration literal for value '" + value + "' does not exist.");
            }
        }
        return result;
    }
}
