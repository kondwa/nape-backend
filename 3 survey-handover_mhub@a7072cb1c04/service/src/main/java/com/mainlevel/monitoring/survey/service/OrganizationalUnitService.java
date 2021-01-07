/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.service;

import com.mainlevel.monitoring.survey.database.node.OrganizationalUnit;

/**
 * Service for maintaining organizational units.
 */
public interface OrganizationalUnitService {

    /**
     * Load the organizational unit by its name.
     *
     * @param name the unit name
     * @return the unit
     */
    OrganizationalUnit loadUnitByName(String name);

    /**
     * Load the organizational unit by its name.
     *
     * @param foreignId the unit foreign id
     * @return the unit
     */
    OrganizationalUnit loadUnitByForeignId(String foreignId);

    /**
     * Save the given unit to the database.
     *
     * @param unit the unit to save
     * @return the saved unit
     */
    OrganizationalUnit save(OrganizationalUnit unit);

    /**
     * Delete the unit with the given foreign id.
     *
     * @param foreignId the unit foreign id
     * @return the deleted unit
     */
    OrganizationalUnit deleteByForeignId(String foreignId);

}
