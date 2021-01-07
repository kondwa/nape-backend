package com.mainlevel.monitoring.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Starts the application.
 */
@SpringBootApplication
public class AuthenticationApplication {

    /**
     * Main method for the application startup.
     *
     * @param args application arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(AuthenticationApplication.class, args);
    }
}
