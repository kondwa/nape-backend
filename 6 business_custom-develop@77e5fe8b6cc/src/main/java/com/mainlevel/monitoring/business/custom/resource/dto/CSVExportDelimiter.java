/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.business.custom.resource.dto;

/**
 * Enumeration for possible csv export types.
 */
public enum CSVExportDelimiter {

    /**
     * Delimiter is a comma ','.
     */
    COMMA(','),

    /**
     * Delimiter is a semicolon ';'.
     */
    SEMICOLON(';'),

    /**
     * Delimiter is a tabulator '\t'.
     */
    TAB('\t');

    private final char delimiter;

    private CSVExportDelimiter(char delimiter) {
        this.delimiter = delimiter;
    }

    /**
     * Gets the delimiter for CSVExportDelimiter.
     *
     * @return delimiter
     */
    public char getDelimiter() {
        return delimiter;
    }

    /**
     * Returns the literal for the given name. When no enum literal exists, a comma will be returned.
     *
     * @param name value to check for
     * @return the enum literal
     */
    public static CSVExportDelimiter getValue(String name) {

        for (CSVExportDelimiter value : values()) {
            if (value.name().equalsIgnoreCase(name)) {
                return value;
            }
        }

        return CSVExportDelimiter.SEMICOLON;
    }

}
