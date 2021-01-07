/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.admin.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.mainlevel.monitoring.admin.repository.entity.UnitEntity;

/**
 * MongoDB repository for {@link UnitEntity}.
 */
public interface UnitRepository extends MongoRepository<UnitEntity, String> {

    /**
     * Find all units associated to a parent unit with given id.
     *
     * @param parentId the parent id
     * @return the list of instances
     */
    @Query(value = "{ 'parent.id' :  ?0 }")
    public List<UnitEntity> findByParentId(String parentId);

    /**
     * Find entity with the given foreignId
     *
     * @param foreignId the foreign id
     * @return the instance
     */
    @Query(value = "{ 'foreignId' :  ?0 }")
    public UnitEntity findByForeignId(String foreignId);

    /**
     * Find root entity.
     *
     * @return the root entity to load
     */
    public UnitEntity findByRootTrue();

    /**
     * Find the units by its type.
     *
     * @param type the unit type
     * @return the list of units
     */
    public List<UnitEntity> findByType(String type);
}
