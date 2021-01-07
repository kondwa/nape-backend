package com.mainlevel.monitoring.common.service;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

/**
 * Common service that converts a collection of objects using the Spring ConversionService for each object.
 */
public interface CollectionConversionService {

    /**
     * Converts the collection to a list of the outgoing type.
     *
     * @param <T1> incoming type of objects
     * @param <T2> outgoing type of objects
     * @param collection the collection that should be converted
     * @param outgoingType the type of the new objects
     * @return a new collection, containing all converted objects
     */
    <T1, T2> List<T2> convert(Collection<T1> collection, Class<T2> outgoingType);

    /***
     * Converts the collection to a list of the outgoing type and includes a consumer for additional mapping.
     *
     * @param <T1> incoming type of objects
     * @param <T2> outgoing type of objects
     * @param collection the collection that should be converted
     * @param outgoingType the type of the new objects
     * @param consumer step into each conversion step to add additional data to the object
     * @return a new collection, containing all converted objects
     */
    <T1, T2> List<T2> convert(Collection<T1> collection, Class<T2> outgoingType, Consumer<T2> consumer);
}
