/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.database.repository;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.mainlevel.monitoring.survey.database.node.OrganizationalUnit;

/**
 * Neo4j repository for loading and maintaining organizational units.
 */
public interface OrganizationalUnitRepository extends GraphRepository<OrganizationalUnit> {

    /**
     * Find the organizational unit by its name.
     *
     * @param name the unit name
     * @return the unit
     */
    OrganizationalUnit findByName(String name);

    /**
     * Find the organizational unit by its foreign id.
     *
     * @param foreignId the foreign id
     * @return the unit
     */
    OrganizationalUnit findByForeignId(String foreignId);

}
