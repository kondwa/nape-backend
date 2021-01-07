package com.mainlevel.monitoring.admin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mainlevel.monitoring.admin.repository.entity.LanguageEntity;

/**
 * MongoDB repository for the {@link LanguageEntity} entity.
 */
public interface LanguageRepository extends MongoRepository<LanguageEntity, String> {

}
