package com.mainlevel.monitoring.authentication.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mainlevel.monitoring.authentication.repository.JwtRepository;
import com.mainlevel.monitoring.authentication.repository.RoleRepository;
import com.mainlevel.monitoring.authentication.repository.UserRepository;

/**
 * JPA database configuration for this application.
 */
@EnableTransactionManagement
@EnableJpaRepositories(basePackageClasses = {RoleRepository.class, UserRepository.class, JwtRepository.class})
@Configuration
public class DatabaseConfiguration {

}
