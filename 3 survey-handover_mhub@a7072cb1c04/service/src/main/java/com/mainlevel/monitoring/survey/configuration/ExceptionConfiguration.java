package com.mainlevel.monitoring.survey.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Base configuration for default exception handling.
 */
@Configuration
@Import(com.mainlevel.monitoring.common.configuration.ExceptionConfiguration.class)
public class ExceptionConfiguration {

}
