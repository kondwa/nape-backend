package com.mainlevel.monitoring.admin.service;

import java.util.Set;

import com.mainlevel.monitoring.admin.repository.entity.IndicatorGroupEntity;

/**
 * Contains business logic for IndicatorEntity.
 */
public interface IndicatorService {

    /**
     * Load all indicator groups visible for the current user.
     *
     * @return the list of indicator groups
     */
    Set<IndicatorGroupEntity> loadGroups();

    /**
     * Save the given indicator group.
     *
     * @param group the group to save
     * @return the saved group
     */
    IndicatorGroupEntity save(IndicatorGroupEntity group);

    /**
     * Loads the given indicator group.
     *
     * @param groupId id of the indicator group
     * @return the group entity
     */
    IndicatorGroupEntity loadIndicatorsGroup(String groupId);

}
