/*
 * Copyright (c) 2018 Mainlevel Consulting AG. All rights reserved.
 */
package com.mainlevel.monitoring.common.converter;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.core.convert.support.DefaultConversionService;

/**
 * DefaultConverter that provides a simple converter with 1:1 mapping using model mapper.
 */
public class SimpleConversionService extends DefaultConversionService {

    @Override
    protected GenericConverter getDefaultConverter(TypeDescriptor sourceType, TypeDescriptor targetType) {
        return new SimpleConverter();
    }

}
