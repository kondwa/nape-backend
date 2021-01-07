package com.mainlevel.monitoring.surveyadmin.configuration;

import org.springframework.context.annotation.ComponentScan;

import com.mainlevel.monitoring.admin.service.impl.SurveyTemplateServiceImpl;

/**
 * Services test configuration.
 */
@ComponentScan(basePackageClasses = SurveyTemplateServiceImpl.class)
public class SurveyServiceTestConfiguration {

}
