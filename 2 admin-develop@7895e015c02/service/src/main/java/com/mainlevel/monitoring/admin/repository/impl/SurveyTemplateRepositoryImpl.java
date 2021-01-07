package com.mainlevel.monitoring.admin.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import org.springframework.data.mongodb.core.query.Update;

import com.mainlevel.monitoring.admin.repository.SurveyTemplateRepositoryCustom;

/**
 * Implementation of custom repository methods.
 * http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.custom-implementations
 */
public class SurveyTemplateRepositoryImpl implements SurveyTemplateRepositoryCustom {

    @Autowired
    private MongoTemplate template;

    @Override
    public void updateStatus(String id, String newStatus) {
        Query query = new Query(where("_id").is(id));
        Update update = new Update();
        update.set("status", newStatus);
        template.updateFirst(query, update, "survey");
    }

}
