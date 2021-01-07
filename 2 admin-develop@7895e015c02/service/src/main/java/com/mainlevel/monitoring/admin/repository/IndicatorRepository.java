package com.mainlevel.monitoring.admin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mainlevel.monitoring.admin.repository.entity.IndicatorGroupEntity;

/**
 * MongoDB repository for {@link IndicatorGroupEntity} entity.
 */
public interface IndicatorRepository extends MongoRepository<IndicatorGroupEntity, String> {

}
