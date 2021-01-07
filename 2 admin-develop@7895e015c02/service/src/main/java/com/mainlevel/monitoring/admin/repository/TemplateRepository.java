/**
 * Mongo repository for the SurveyTemplate entity.
 */
package com.mainlevel.monitoring.admin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.mainlevel.monitoring.admin.repository.entity.TemplateEntity;

/**
 * MongoDB repository for {@link TemplateEntity} entity.
 */
public interface TemplateRepository extends MongoRepository<TemplateEntity, String> {

    /**
     * Find templates by given surveyId.
     *
     * @param active active flag
     * @param surveyId id of the survey
     * @param pageable pagable information
     * @return the page
     */
    @Query(value = "{ 'active': ?0, 'survey.id' :  ?1 }")
    public Page<TemplateEntity> findBySurvey(boolean active, String surveyId, Pageable pageable);

    /**
     * Find templates active or inactive('deleted'), by given id, and version
     *
     * @param active - active/inactive flag
     * @param surveyId - id of a survey
     * @param version - eager version
     * @param pageable - paging props
     * @return all SurveyTemplates in Page which are fit with given parameters.
     */
    @Query(value = "{ 'active': ?0, 'survey.id' :  ?1, 'version' : ?2 }")
    public Page<TemplateEntity> findBySurveyAndVersion(boolean active, String surveyId, Long version, Pageable pageable);
}
