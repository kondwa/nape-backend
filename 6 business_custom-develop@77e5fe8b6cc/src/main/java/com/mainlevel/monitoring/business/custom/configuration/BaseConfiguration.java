package com.mainlevel.monitoring.business.custom.configuration;

import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.mainlevel.monitoring.common.configuration.annotation.EnableConversionServices;

/**
 * Base configuration for the survey microservice.
 */
@EnableConversionServices
@Configuration
public class BaseConfiguration {

    /**
     * Enables the RestTemplate.
     *
     * @return a new rest template bean
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

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
