package com.mainlevel.monitoring.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mainlevel.monitoring.admin.repository.LanguageRepository;
import com.mainlevel.monitoring.admin.repository.entity.LanguageEntity;
import com.mainlevel.monitoring.admin.service.LanguageService;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of {@link LanguageService}.
 */
@Slf4j
@Service
public class LanguageServiceImpl implements LanguageService {

    @Autowired
    private LanguageRepository repository;

    @Override
    public List<LanguageEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public LanguageEntity save(LanguageEntity language) {
        if (!repository.exists(language.getId())) {
            language = repository.save(language);

            log.debug("Saved Language {}.", language);

        }
        return language;
    }

    @Override
    public LanguageEntity load(final String id) {
        return repository.findOne(id);
    }

}
