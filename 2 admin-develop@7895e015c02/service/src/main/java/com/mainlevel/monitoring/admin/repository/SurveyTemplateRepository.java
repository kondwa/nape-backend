package com.mainlevel.monitoring.admin.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.mainlevel.monitoring.admin.repository.entity.SurveyTemplateEntity;

/**
 * Mongo repository for the SurveyEntity entity.
 */
public interface SurveyTemplateRepository extends MongoRepository<SurveyTemplateEntity, String>, SurveyTemplateRepositoryCustom {

    /**
     * Method returns all non-deleted(active) surveys.
     *
     * @return all non-deleted(active) surveys.
     */
    public List<SurveyTemplateEntity> findByActiveIsTrue();

    /**
     * Find surveys that match with given arguments/properties.
     *
     * @param active is survey active or not.
     * @param unitId survey in the given unit with this id
     * @return list of surveys, never return null.
     */
    @Query(value = "{ 'active': ?0, 'unit.id' :  ?1 }")
    public List<SurveyTemplateEntity> findByActiveAndUnitId(Boolean active, String unitId);

}
