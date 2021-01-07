package com.mainlevel.monitoring.survey.configuration;

import org.springframework.context.annotation.ComponentScan;

import com.mainlevel.monitoring.survey.service.impl.SurveyServiceImpl;

/**
 * Test configuration for the SurveyService.
 */
@ComponentScan(basePackageClasses = SurveyServiceImpl.class)
public class SurveyServiceTestConfiguration {

}
