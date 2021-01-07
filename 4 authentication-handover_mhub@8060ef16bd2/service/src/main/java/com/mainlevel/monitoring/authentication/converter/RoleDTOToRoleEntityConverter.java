package com.mainlevel.monitoring.authentication.converter;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.authentication.api.dto.RoleDTO;
import com.mainlevel.monitoring.authentication.repository.entity.RoleEntity;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;

/**
 * Converter converting {@link RoleDTO} to {@link RoleEntity}.
 */
@Component
public class RoleDTOToRoleEntityConverter extends AbstractApplicationAwareConverter<RoleDTO, RoleEntity> {

    @Override
    public RoleEntity convert(final RoleDTO roleDTO) {
        return RoleEntity.builder().id(roleDTO.getIdentifier()).name(roleDTO.getName()).build();
    }

}
