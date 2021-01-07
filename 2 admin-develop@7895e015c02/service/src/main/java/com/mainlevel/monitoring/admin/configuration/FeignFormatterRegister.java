package com.mainlevel.monitoring.admin.configuration;

import org.springframework.cloud.netflix.feign.FeignFormatterRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;

import com.mainlevel.monitoring.common.util.DateUrlFormatter;

/**
 * Feign client parameters formatter.
 */
@Configuration
public class FeignFormatterRegister implements FeignFormatterRegistrar {

    @Override
    public void registerFormatters(FormatterRegistry registry) {
        registry.addFormatter(new DateUrlFormatter());
    }

}
