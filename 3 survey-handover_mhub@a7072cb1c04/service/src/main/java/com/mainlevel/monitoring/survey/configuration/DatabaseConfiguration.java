package com.mainlevel.monitoring.survey.configuration;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.mainlevel.monitoring.survey.database.node.Survey;
import com.mainlevel.monitoring.survey.database.queryresult.ReportingPeriodOverview;
import com.mainlevel.monitoring.survey.database.repository.SurveyRepository;

/**
 * Database configuration for this application
 */
@EnableTransactionManagement
@EnableNeo4jRepositories(basePackageClasses = SurveyRepository.class)
@Configuration
public class DatabaseConfiguration {

    @Autowired
    private Neo4jProperties neo4jProperties;

    /**
     * Creates the Neo4j database configuration based on properties.
     *
     * @return the neo4j configuration
     */
    @Bean
    public org.neo4j.ogm.config.Configuration getConfiguration() {
        final org.neo4j.ogm.config.Configuration config = new org.neo4j.ogm.config.Configuration();
        config.driverConfiguration().setDriverClassName("org.neo4j.ogm.drivers.http.driver.HttpDriver").setURI(neo4jProperties.getNeo4jUri());
        return config;
    }

    /**
     * Create a new Neo4j session factory.
     *
     * @return the neo4j session factory bean
     */
    @Bean
    public SessionFactory getSessionFactory() {
        return new SessionFactory(getConfiguration(), Survey.class.getPackage().getName(), ReportingPeriodOverview.class.getPackage().getName());
    }

    /**
     * Create a new Neo4j transaction manager.
     *
     * @return the neo4j transaction manager bean
     */
    @Bean(name = "transactionManager")
    public Neo4jTransactionManager getTransactionManager() {
        return new Neo4jTransactionManager(getSessionFactory());
    }

    /**
     * Add a validator factory for bean validation.
     *
     * @return the factory bean
     */
    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

}
