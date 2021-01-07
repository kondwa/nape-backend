package com.mainlevel.monitoring.surveyadmin.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import com.mainlevel.monitoring.admin.configuration.BaseConfiguration;
import com.mainlevel.monitoring.admin.configuration.MVCConfiguration;
import com.mainlevel.monitoring.admin.converter.TemplateDTOToTemplateEntityConverter;

/**
 * Test configuration for resources.
 */
@ComponentScan(basePackageClasses = TemplateDTOToTemplateEntityConverter.class)
@Import({BaseConfiguration.class, MVCConfiguration.class})
public class ResourceTestConfiguration {

}
