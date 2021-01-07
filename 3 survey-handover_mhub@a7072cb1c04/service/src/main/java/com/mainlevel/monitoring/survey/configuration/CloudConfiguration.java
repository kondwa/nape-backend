package com.mainlevel.monitoring.survey.configuration;

import org.springframework.context.annotation.Configuration;

import com.mainlevel.monitoring.common.configuration.annotation.EnableCloudServices;

/**
 * Cloud configuration enabling feign and eureka for survey microservice.
 */
@EnableCloudServices
@Configuration
public class CloudConfiguration {

}
