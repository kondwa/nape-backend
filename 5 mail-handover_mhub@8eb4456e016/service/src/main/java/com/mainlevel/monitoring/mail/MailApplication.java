package com.mainlevel.monitoring.mail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Spring Boot MailApplication Startup Point.
 */
@SpringBootApplication
@EnableAsync
@EnableCaching
public class MailApplication {

    /**
     * Starts the mail microservice.
     *
     * @param args the application arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(MailApplication.class, args);
    }
}
