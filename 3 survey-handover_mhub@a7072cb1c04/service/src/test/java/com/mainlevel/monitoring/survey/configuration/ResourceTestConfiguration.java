package com.mainlevel.monitoring.survey.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import com.mainlevel.monitoring.survey.converter.ReportingPeriodOverviewToReportingPeriodOverviewDTOConverter;

/**
 * Common test configuration for REST resources.
 */
@ComponentScan(basePackageClasses = ReportingPeriodOverviewToReportingPeriodOverviewDTOConverter.class)
@Import({BaseConfiguration.class, MVCConfiguration.class})
public class ResourceTestConfiguration {

}
