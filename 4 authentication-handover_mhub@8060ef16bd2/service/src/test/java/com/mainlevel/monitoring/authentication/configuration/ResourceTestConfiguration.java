package com.mainlevel.monitoring.authentication.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import com.mainlevel.monitoring.authentication.configuration.BaseConfiguration;
import com.mainlevel.monitoring.authentication.configuration.MVCConfiguration;
import com.mainlevel.monitoring.authentication.converter.RoleDTOToRoleEntityConverter;

@SuppressWarnings("javadoc")
@ComponentScan(basePackageClasses = RoleDTOToRoleEntityConverter.class)
@Import({BaseConfiguration.class, MVCConfiguration.class})
public class ResourceTestConfiguration {

}
