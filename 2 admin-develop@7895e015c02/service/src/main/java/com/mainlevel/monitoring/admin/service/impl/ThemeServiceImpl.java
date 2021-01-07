/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mainlevel.monitoring.admin.repository.ThemeRepository;
import com.mainlevel.monitoring.admin.repository.entity.ThemeEntity;
import com.mainlevel.monitoring.admin.service.ThemeService;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of {@link ThemeService}.
 */
@Slf4j
@Service
public class ThemeServiceImpl implements ThemeService {

    private static final String DEFAULT_THEME = "ml";

    @Autowired
    private ThemeRepository themeRepository;

    @Override
    public ThemeEntity loadThemeByName(String name) {
        ThemeEntity theme;

        if (name == null) {
            log.warn("No theme name provided, loading default theme.");
            theme = themeRepository.findByName(DEFAULT_THEME);
        } else {
            theme = themeRepository.findByName(name);

            if (theme == null) {
                log.warn("Theme {} not found, loading default theme.", name);
                theme = themeRepository.findByName(DEFAULT_THEME);
            }
        }

        return theme;
    }

}
