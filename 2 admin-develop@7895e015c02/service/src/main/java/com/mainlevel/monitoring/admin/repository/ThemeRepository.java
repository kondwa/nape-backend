/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.admin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mainlevel.monitoring.admin.repository.entity.ThemeEntity;

/**
 * MongoDB repository for loading and maintaining themes.
 */
public interface ThemeRepository extends MongoRepository<ThemeEntity, String> {

    /**
     * Find the given theme entity by its name.
     *
     * @param name the theme name
     * @return the theme entity
     */
    ThemeEntity findByName(String name);
}
