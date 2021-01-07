package com.mainlevel.monitoring.authentication.converter;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.authentication.api.dto.RoleDTO;
import com.mainlevel.monitoring.authentication.repository.entity.RoleEntity;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;

/**
 * Converter converting {@link RoleEntity} to {@link RoleDTO}.
 */
@Component
public class RoleEntityToRoleDTOConverter extends AbstractApplicationAwareConverter<RoleEntity, RoleDTO> {

    @Override
    public RoleDTO convert(RoleEntity roleEntity) {
        return RoleDTO.builder().identifier(roleEntity.getId()).name(roleEntity.getName()).build();
    }
}
