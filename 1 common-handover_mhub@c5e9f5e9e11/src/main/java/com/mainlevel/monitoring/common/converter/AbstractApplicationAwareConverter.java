package com.mainlevel.monitoring.common.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;

import com.mainlevel.monitoring.common.service.CollectionConversionService;

/**
 * Base class for type converters.
 *
 * @param <T1> source type
 * @param <T2> result type
 */
abstract public class AbstractApplicationAwareConverter<T1, T2> implements Converter<T1, T2>, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * Getter for the application context.
     *
     * @return the context
     */
    protected ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * Getter for the conversion service.
     *
     * @return the service bean
     */
    protected ConversionService getConversionService() {
        return getApplicationContext().getBean(DefaultConversionService.class);
    }

    /**
     * Getter for the collection conversion service.
     *
     * @return the service bean
     */
    protected CollectionConversionService getCollectionConversionService() {
        return getApplicationContext().getBean(CollectionConversionService.class);
    }

    /**
     * Getter for the model mapper.
     * 
     * @return the model mapper bean
     */
    protected ModelMapper getModelMapper() {
        return getApplicationContext().getBean(ModelMapper.class);
    }
}
