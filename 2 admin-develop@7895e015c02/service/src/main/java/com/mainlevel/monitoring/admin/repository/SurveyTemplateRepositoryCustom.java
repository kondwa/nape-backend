package com.mainlevel.monitoring.admin.repository;

/**
 * Contains custom(non-default) query/update operation for MongoDb.
 * http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.custom-implementations
 */
public interface SurveyTemplateRepositoryCustom {

    /**
     * Set new status to the Survey with given id.
     *
     * @param id key
     * @param newStatus a new status of survey.
     */
    void updateStatus(String id, String newStatus);
}
