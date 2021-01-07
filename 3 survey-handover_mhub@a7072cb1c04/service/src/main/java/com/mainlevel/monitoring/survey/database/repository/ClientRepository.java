/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.database.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import com.mainlevel.monitoring.survey.api.dto.ClientType;
import com.mainlevel.monitoring.survey.database.node.Client;

/**
 * Neo4j repository to maintain and laod client nodes.
 */
public interface ClientRepository extends GraphRepository<Client> {

    /**
     * Load the client for the given parameters.
     *
     * @param address the ip address
     * @param browser the client browser
     * @param os the client operating system
     * @param type the client type
     * @return the client or null
     */
    @Query("MATCH (c:Client) where c.address = {address} and c.type = {type} and c.browser = {browser} and c.os = {os} return c")
    Client findByAttributes(@Param("address") String address, @Param("browser") String browser, @Param("os") String os,
        @Param("type") ClientType type);
}
