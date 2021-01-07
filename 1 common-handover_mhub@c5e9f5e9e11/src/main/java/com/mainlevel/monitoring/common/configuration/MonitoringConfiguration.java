package com.mainlevel.monitoring.common.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.EnableMBeanExport;

import com.mainlevel.monitoring.common.monitoring.aspect.PerformanceAspect;
import com.mainlevel.monitoring.common.monitoring.jmx.PerformanceMonitorJMX;

/**
 * Common configuration for monitoring.
 */
@EnableMBeanExport
@EnableAspectJAutoProxy
@ComponentScan(basePackageClasses = {PerformanceAspect.class, PerformanceMonitorJMX.class})
@Configuration
public class MonitoringConfiguration {

}
