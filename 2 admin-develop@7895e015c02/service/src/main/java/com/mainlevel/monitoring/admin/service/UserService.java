/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.admin.service;

import com.mainlevel.monitoring.admin.repository.entity.UserEntity;

/**
 * Service for maintaining user entities.
 */
public interface UserService {

    /**
     * Saves a user to the database. When the user with the given userid already exists the attribues are updated.
     *
     * @param user the user to save
     * @return the saved user
     */
    UserEntity save(UserEntity user);

    /**
     * Loads the given user by its user name.
     *
     * @param userName the userName
     * @return the user or null if not existent
     */
    UserEntity findByUserName(String userName);

}
