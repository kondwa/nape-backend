/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */
package com.mainlevel.monitoring.business.custom.resource.dto.template;

/**
 * Status of a template.
 */
public enum CustomTemplateStatus {

    /**
     * Template is active.
     */
    ACTIVE("active"),

    /**
     * Template is deactivated.
     */
    DEACTIVATED("inactive");

    private CustomTemplateStatus(String name) {
        this.name = name;
    }

    private String name;

    /**
     * Gets the name for CustomTemplateStatus.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Parse the template status string to enum
     *
     * @param status the status string
     * @return the enumeration
     */
    public static CustomTemplateStatus valueOfTemplateStatus(String status) {
        for (CustomTemplateStatus value : CustomTemplateStatus.values()) {
            if (value.getName().equalsIgnoreCase(status)) {
                return value;
            }
        }
        return null;
    }

}
