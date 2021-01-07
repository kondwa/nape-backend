package com.mainlevel.monitoring.common.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import com.mainlevel.monitoring.common.service.CollectionConversionService;

/**
 * Converts a collection of objects using the Spring ConversionService for each
 * object.
 */
@Service
public class CollectionConversionServiceImpl implements CollectionConversionService {

    @Autowired
    private ConversionService conversionService;

    @Override
    public <T1, T2> List<T2> convert(Collection<T1> collection, Class<T2> outgoingType) {
        return this.convert(collection, outgoingType, c -> {
            /* do nothing */ });
    }

    @Override
    public <T1, T2> List<T2> convert(Collection<T1> collection, Class<T2> outgoingType, Consumer<T2> consumer) {
        final List<T2> outgoingCollection = new ArrayList<>();

        if (collection != null) {
            for (T1 incomingObject : collection) {
                T2 convertedObject = conversionService.convert(incomingObject, outgoingType);

                if (convertedObject != null) {
                    if (consumer != null) {
                        consumer.accept(convertedObject);
                    }
                    outgoingCollection.add(convertedObject);
                }
            }
        }
        return outgoingCollection;
    }
}
