package com.mainlevel.monitoring.survey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Spring Boot SurveyApplication Startup Point.
 */
@SpringBootApplication
@EnableCaching
public class SurveyApplication {

    /**
     * Starts the survey microservice.
     *
     * @param args the application arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(SurveyApplication.class, args);
    }
}
