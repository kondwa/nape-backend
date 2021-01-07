package com.mainlevel.monitoring.authentication.converter;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.authentication.api.dto.UserDTO;
import com.mainlevel.monitoring.authentication.repository.entity.RoleEntity;
import com.mainlevel.monitoring.authentication.repository.entity.UserEntity;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;

/**
 * Converter converting {@link UserDTO} to {@link UserEntity}.
 */
@Component
public class UserDTOToUserEntityConverter extends AbstractApplicationAwareConverter<UserDTO, UserEntity> {

    @Override
    public UserEntity convert(final UserDTO source) {

        List<RoleEntity> roles = getCollectionConversionService().convert(source.getRoles(), RoleEntity.class);

        String userType = source.getType() != null ? source.getType().name() : null;
        String authType = source.getType() != null ? source.getAuthentication().name() : null;
        Timestamp expiry = source.getExpiryTime() != null ? new Timestamp(source.getExpiryTime()) : null;

        UserEntity result = UserEntity.builder().id(source.getIdentifier()).username(source.getUsername()).firstname(source.getFirstName())
            .lastname(source.getLastName()).password(source.getPassword()).active(source.getActive()).userType(userType).authentication(authType)
            .expiryDate(expiry).roles(roles).theme(source.getTheme()).build();

        return result;
    }
}
