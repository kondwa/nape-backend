package com.mainlevel.monitoring.common.configuration;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mainlevel.monitoring.common.service.LinkDiscoveryService;
import com.mainlevel.monitoring.common.service.impl.LinkDiscoveryServiceImpl;

/**
 * Cloud configuration that provides the discovery services.
 */
@EnableDiscoveryClient
@Configuration
public class CloudConfiguration {

    /**
     * Provdides the link discovery service.
     *
     * @return the bean
     */
    @Bean
    public LinkDiscoveryService linkDiscoveryService() {
        return new LinkDiscoveryServiceImpl();
    }
}
