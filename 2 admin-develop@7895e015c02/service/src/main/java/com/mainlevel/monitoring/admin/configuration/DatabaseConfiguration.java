package com.mainlevel.monitoring.admin.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mainlevel.monitoring.admin.repository.SurveyTemplateRepository;

/**
 * Database configuration for this application
 */
@EnableTransactionManagement
@EnableMongoRepositories(basePackageClasses = {SurveyTemplateRepository.class})
@Configuration
@EnableMongoAuditing
public class DatabaseConfiguration {

}
