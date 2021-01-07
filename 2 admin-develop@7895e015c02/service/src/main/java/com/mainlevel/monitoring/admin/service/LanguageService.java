package com.mainlevel.monitoring.admin.service;

import java.util.List;

import com.mainlevel.monitoring.admin.repository.entity.LanguageEntity;

/**
 * Service for maintaining and loading of languages.
 */
public interface LanguageService {

    /**
     * Loads all existing language entities.
     *
     * @return the list of entities
     */
    public List<LanguageEntity> findAll();

    /**
     * Save a language entity
     *
     * @param language the language to save
     * @return the saved language
     */
    public LanguageEntity save(LanguageEntity language);

    /**
     * Load a language by its id.
     *
     * @param id the language id
     * @return the loaded language
     */
    public LanguageEntity load(String id);
}
