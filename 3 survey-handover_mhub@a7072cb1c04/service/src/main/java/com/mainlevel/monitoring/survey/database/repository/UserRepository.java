/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.database.repository;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import com.mainlevel.monitoring.survey.api.dto.user.SurveyUserType;
import com.mainlevel.monitoring.survey.database.node.User;

/**
 * Repository for obtaining/maintaining person nodes.
 */
public interface UserRepository extends GraphRepository<User> {

    /**
     * Find a Person by its username.
     *
     * @param username the username of this person
     * @return the person or null if no person with the given username exists
     */
    @Query("MATCH (n:`User`) WHERE n.`username` = {username} WITH n MATCH p=(n)-[:participates*0..1]-(m) RETURN p, ID(n)")
    User findByUsername(@Param("username") String username);

    /**
     * Find users by their type.
     *
     * @param type type of the user
     * @return the list of users for this type
     */
    List<User> findByType(SurveyUserType type);

    /**
     * Loads the list of users participating on an organizational unit.
     *
     * @param unitForeignId foreign id of the org unit
     * @return the list of users for this unit
     */
    @Query("match p=(u:User)-[:participates]->(ou:OrganizationalUnit) where ou.foreignId = {foreignId} return u, nodes(p), rels(p)")
    List<User> findByUnitForeignId(@Param("foreignId") String unitForeignId);

    /**
     * Create the relation between org unit and user.
     *
     * @param unitForeignId foreign id of the unit
     * @param users list of users to maintain
     * @return the list of mapped users
     */
    @Query("unwind {users} as n merge (u:User {name: n.name, username: n.username, type: 'INTERNAL'}) with u match (ou:OrganizationalUnit) where ou.foreignId = {foreignId} with u, ou create (u)-[r:participates {role: 'ASSISTANT'}]->(ou) return u")
    List<User> createUserUnitRelation(@Param("foreignId") String unitForeignId, @Param("users") List<User> users);

    /**
     * Delete the relation between user and unit.
     *
     * @param foreignId the unit foreign id
     * @param graphId the user id
     */
    @Query("match (u:User)-[r:participates]->(ou:OrganizationalUnit) where id(u) = {gid} and ou.foreignId = {foreignId} delete r")
    void deleteUserUnitParticipation(@Param("foreignId") String foreignId, @Param("gid") Long graphId);

}
