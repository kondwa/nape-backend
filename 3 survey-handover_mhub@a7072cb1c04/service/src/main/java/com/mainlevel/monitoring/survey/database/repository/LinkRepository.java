/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.database.repository;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import com.mainlevel.monitoring.survey.database.node.Link;

/**
 * Neo4j repository for maintaining links.
 */
public interface LinkRepository extends GraphRepository<Link> {

    /**
     * Load the links for the user with username.
     *
     * @param username name of the user
     * @return the links for this user
     */
    @Query("MATCH (l:Link)-[:forUser]->(u:User) where u.username = {username} return l")
    List<Link> findAllUserLinks(@Param("username") String username);

    /**
     * Load the link for the given token.
     *
     * @param token the token
     * @return the link, or null if not found
     */
    Link findByToken(String token);

    /**
     * Load the link for an anonymous survey.
     *
     * @param surveyGid the survey graph id
     * @return the link for this survey
     */
    @Query("MATCH (l:Link)-[:forSurvey]->(s:Survey) where id(s) = {surveyGid} and s.visibility = 'ANONYMOUS' return l")
    Link loadLinkForAnonymousSurvey(@Param("surveyGid") Long surveyGid);

}
