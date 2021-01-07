/*
 * Copyright (c) 2018 Mainlevel Consulting AG. All rights reserved.
 */
package com.mainlevel.monitoring.admin.converter;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.admin.api.dto.dashboard.DashboardDTO;
import com.mainlevel.monitoring.admin.repository.entity.DashboardEntity;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;

/**
 * Converter converting {@link DashboardEntity} to {@link DashboardDTO}.
 */
@Component
public class DashboardEntityToDashboardDTOConverter extends AbstractApplicationAwareConverter<DashboardEntity, DashboardDTO> {

    @Override
    public DashboardDTO convert(DashboardEntity source) {
        DashboardDTO target = super.getModelMapper().map(source, DashboardDTO.class);
        return target;
    }

}
