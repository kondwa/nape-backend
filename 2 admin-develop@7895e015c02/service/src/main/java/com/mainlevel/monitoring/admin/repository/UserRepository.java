/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.admin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mainlevel.monitoring.admin.repository.entity.UserEntity;

/**
 * MongoDB repository for maintaining user entities.
 */
public interface UserRepository extends MongoRepository<UserEntity, String> {

    /**
     * Find the given user entity by its user name.
     *
     * @param userName the user id
     * @return the user entity
     */
    UserEntity findByUserName(String userName);

}
