package com.mainlevel.monitoring.business.custom.configuration;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

import com.mainlevel.monitoring.admin.api.resource.TemplateResource;
import com.mainlevel.monitoring.authentication.api.resource.TokenResource;
import com.mainlevel.monitoring.common.configuration.annotation.EnableCloudServices;
import com.mainlevel.monitoring.mail.api.resource.MailResource;
import com.mainlevel.monitoring.survey.api.resource.ReportingPeriodResource;

/**
 * Cloud configuration enabling feign and eureka for survey microservice.
 */
@EnableCloudServices
@Configuration
@EnableFeignClients(basePackageClasses = {TokenResource.class, TemplateResource.class, ReportingPeriodResource.class, MailResource.class})
public class CloudConfiguration {

}
