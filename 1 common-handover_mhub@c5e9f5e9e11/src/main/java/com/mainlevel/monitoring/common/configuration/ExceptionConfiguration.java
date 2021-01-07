package com.mainlevel.monitoring.common.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.mainlevel.monitoring.common.exception.handler.HttpExceptionHandler;
import com.mainlevel.monitoring.common.header.ContentTypeJSONHeader;

/**
 * Common configuration for exception handlers.
 */
@Configuration
@ComponentScan(basePackageClasses = {HttpExceptionHandler.class, ContentTypeJSONHeader.class})
public class ExceptionConfiguration {

}
