package com.mainlevel.monitoring.admin.repository.entity;

/**
 * Survey template status types.
 */
public enum TemplateStatus {

    /**
     * Survey is in draft state and has not been activated.
     */
    DRAFT("draft"),

    /**
     * Survey has been activated and is already in use.
     */
    ACTIVE("active");

    private String name;

    /**
     * Constructor for TemplateStatus.
     *
     * @param name status name
     */
    private TemplateStatus(String name) {
        this.name = name;
    }

    /**
     * Gets the name for TemplateStatus.
     *
     * @return name of the status
     */
    public String getName() {
        return name;
    }

}
