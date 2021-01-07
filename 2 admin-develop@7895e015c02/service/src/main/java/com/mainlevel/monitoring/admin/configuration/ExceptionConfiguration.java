package com.mainlevel.monitoring.admin.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Default exception configuration for survey admin.
 */
@Configuration
@Import(com.mainlevel.monitoring.common.configuration.ExceptionConfiguration.class)
public class ExceptionConfiguration {

}
