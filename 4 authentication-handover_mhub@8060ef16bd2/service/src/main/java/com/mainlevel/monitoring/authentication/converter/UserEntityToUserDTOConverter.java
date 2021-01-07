package com.mainlevel.monitoring.authentication.converter;

import java.util.List;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.authentication.api.dto.AuthenticationType;
import com.mainlevel.monitoring.authentication.api.dto.RoleDTO;
import com.mainlevel.monitoring.authentication.api.dto.UserDTO;
import com.mainlevel.monitoring.authentication.api.dto.UserType;
import com.mainlevel.monitoring.authentication.repository.entity.UserEntity;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;

/**
 * Converter converting {@link UserEntity} to {@link UserDTO}.
 */
@Component
public class UserEntityToUserDTOConverter extends AbstractApplicationAwareConverter<UserEntity, UserDTO> {

    @Override
    public UserDTO convert(final UserEntity source) {
        List<RoleDTO> roles = getCollectionConversionService().convert(source.getRoles(), RoleDTO.class);

        Long expiry = source.getExpiryDate() != null ? source.getExpiryDate().getTime() : null;

        UserType userType = source.getUserType() != null ? UserType.valueOf(source.getUserType()) : null;
        AuthenticationType authType = source.getUserType() != null ? AuthenticationType.valueOf(source.getAuthentication()) : null;

        UserDTO result = UserDTO.builder().identifier(source.getId()).username(source.getUsername()).firstName(source.getFirstname())
            .lastName(source.getLastname()).active(source.getActive()).type(userType).authentication(authType).expiryTime(expiry).roles(roles)
            .theme(source.getTheme()).build();

        return result;
    }
}
