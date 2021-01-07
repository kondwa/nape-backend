package com.mainlevel.monitoring.business.custom.configuration;

import org.springframework.context.annotation.Configuration;

import com.mainlevel.monitoring.common.configuration.annotation.EnableMonitoringServices;

/**
 * Base configuration for monitoring enabling JMX and performance aspects.
 */
@EnableMonitoringServices
@Configuration
public class MonitoringConfiguration {

}
