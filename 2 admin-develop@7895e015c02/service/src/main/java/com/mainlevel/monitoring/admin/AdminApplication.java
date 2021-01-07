package com.mainlevel.monitoring.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

/**
 * Spring Boot Application Startup Point.
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class AdminApplication {

    /**
     * Starts the survey admin microservice.
     *
     * @param args the application arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
