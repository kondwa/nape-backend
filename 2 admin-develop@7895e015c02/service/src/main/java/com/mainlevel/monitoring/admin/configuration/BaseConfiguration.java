package com.mainlevel.monitoring.admin.configuration;

import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mainlevel.monitoring.common.configuration.annotation.EnableConversionServices;

/**
 * Base configuration for survey admin application.
 */
@EnableConversionServices
@Configuration
public class BaseConfiguration {

    /**
     * Enable the Zipkin sampler.
     *
     * @return always sampler that samples everything
     */
    @Bean
    public AlwaysSampler defaultSampler() {
        return new AlwaysSampler();
    }

}
