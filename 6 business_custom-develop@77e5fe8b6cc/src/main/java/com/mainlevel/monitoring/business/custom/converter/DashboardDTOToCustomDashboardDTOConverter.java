/*
 * Copyright (c) 2018 Mainlevel Consulting AG. All rights reserved.
 */
package com.mainlevel.monitoring.business.custom.converter;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.admin.api.dto.dashboard.DashboardDTO;
import com.mainlevel.monitoring.business.custom.resource.dto.dashboard.CustomDashboardDTO;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;

/**
 * Converter converting {@link DashboardDTO} to {@link CustomDashboardDTO}.
 */
@Component
public class DashboardDTOToCustomDashboardDTOConverter extends AbstractApplicationAwareConverter<DashboardDTO, CustomDashboardDTO> {

    @Override
    public CustomDashboardDTO convert(DashboardDTO source) {
        CustomDashboardDTO target = super.getModelMapper().map(source, CustomDashboardDTO.class);
        return target;
    }

}
