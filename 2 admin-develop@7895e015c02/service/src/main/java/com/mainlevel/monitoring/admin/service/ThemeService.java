/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.admin.service;

import com.mainlevel.monitoring.admin.repository.entity.ThemeEntity;

/**
 * Service for loading and maintaining app themes.
 */
public interface ThemeService {

    /**
     * Load the theme with the given name.
     * 
     * @param name the theme name
     * @return the theme entity
     */
    ThemeEntity loadThemeByName(String name);
}
