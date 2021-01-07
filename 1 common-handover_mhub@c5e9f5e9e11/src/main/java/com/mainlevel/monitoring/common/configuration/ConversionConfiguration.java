package com.mainlevel.monitoring.common.configuration;

import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;

import com.mainlevel.monitoring.common.converter.SimpleConversionService;
import com.mainlevel.monitoring.common.service.CollectionConversionService;
import com.mainlevel.monitoring.common.service.impl.CollectionConversionServiceImpl;

/**
 * Common configuration for type conversion services.
 */
@Configuration
public class ConversionConfiguration {

    /**
     * Provides the conversion service.
     *
     * @param converters the list of converters
     * @return the bean
     */
    @Bean
    public ConversionService conversionService(Set<Converter<?, ?>> converters) {
        final DefaultConversionService defaultConversionService = new SimpleConversionService();
        converters.forEach(c -> defaultConversionService.addConverter(c));
        return defaultConversionService;
    }

    /**
     * Provides the collection conversion service.
     *
     * @return the bean
     */
    @Bean
    public CollectionConversionService collectionConversionService() {
        return new CollectionConversionServiceImpl();
    }

    /**
     * Provides a model mapper instance.
     *
     * @return the model mapper bean
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
