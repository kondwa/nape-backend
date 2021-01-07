/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.admin.service;

import java.util.List;

import com.mainlevel.monitoring.admin.repository.entity.UnitEntity;

/**
 * Service for maintaining organizational units.
 */
public interface UnitService {

    /**
     * Find the organizational unit by its id.
     *
     * @param id ID of the organizational unit
     * @return the unit
     */
    UnitEntity findUnitById(String id);

    /**
     * Find the organizational unit by its forgeign id.
     *
     * @param foreignId foreign ID of the organizational unit
     * @return the unit
     */
    UnitEntity findUnitByForeignId(String foreignId);

    /**
     * Save the organizational unit to database.
     *
     * @param unit the unit to save
     * @return the saved unit
     */
    UnitEntity saveByForeignId(UnitEntity unit);

    /**
     * Load the root organizational unit with all sub entities available for the current user.
     *
     * @param username the username
     * @return the root unit
     */
    UnitEntity findDeepForUser(String username);

    /**
     * Delete the unit with the given key.
     *
     * @param foreignId the key of the unit
     * @return the deleted entity
     */
    UnitEntity deleteByForeignId(String foreignId);

    /**
     * Load the units for the given type.
     *
     * @param type type of unit
     * @return the list of units
     */
    List<UnitEntity> findUnitsByType(String type);

    /**
     * Loads the unit including its children.
     * 
     * @param unit the unit to load
     * @return the loaded unit
     */
    UnitEntity loadDeep(UnitEntity unit);

}
