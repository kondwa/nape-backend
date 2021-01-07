/*
 * Copyright (c) 2018 Mainlevel Consulting AG. All rights reserved.
 */
package com.mainlevel.monitoring.common.converter;

import java.util.Collections;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

/**
 * Converter that converts 1:1 depending on model mapper.
 */
public class SimpleConverter implements GenericConverter {

    private ModelMapper mapper = new ModelMapper();

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(Object.class, Object.class));
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        return mapper.map(source, targetType.getObjectType());
    }

}
