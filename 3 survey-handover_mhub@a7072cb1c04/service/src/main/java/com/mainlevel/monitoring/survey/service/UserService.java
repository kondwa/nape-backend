/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.survey.service;

import java.util.List;

import com.mainlevel.monitoring.survey.database.node.User;

/**
 * Service for managing user instances.
 */
public interface UserService {

    /**
     * Retrive the current user instance.
     *
     * @return the user instance associated with the current login
     */
    User getCurrentUser();

    /**
     * Retrieve the unique user instance of the anonymous user.
     *
     * @return the anonymous user instance
     */
    User getAnonymousUser();

    /**
     * Creates the user in neo4j.
     *
     * @param user the user to create
     * @return the created user
     */
    User saveUser(User user);

    /**
     * Find a user by its username.
     *
     * @param username username of the user
     * @return the user with the given username, or null
     */
    User getUserByUsername(String username);

    /**
     * Load the users participating the given organizational unit.
     *
     * @param unitForeignId foreign id of the organizational unit
     * @return the list of users
     */
    List<User> getUsersForUnit(String unitForeignId);

    /**
     * Create the relation between user and organizational unit.
     *
     * @param foreignId the unit foreign id
     * @param users list of users
     * @return list of users
     */
    List<User> setUsersForUnit(String foreignId, List<User> users);

    /**
     * Delete the relation between user and org unit.
     *
     * @param foreignId org unit foreign id
     * @param graphId user gid
     */
    void deleteUserUnitParticipation(String foreignId, Long graphId);

}
