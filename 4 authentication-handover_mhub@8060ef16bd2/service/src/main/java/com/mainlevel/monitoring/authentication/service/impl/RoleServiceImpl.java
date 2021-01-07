package com.mainlevel.monitoring.authentication.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mainlevel.monitoring.authentication.api.dto.RoleDTO;
import com.mainlevel.monitoring.authentication.repository.RoleRepository;
import com.mainlevel.monitoring.authentication.repository.entity.RoleEntity;
import com.mainlevel.monitoring.authentication.service.RoleService;
import com.mainlevel.monitoring.common.service.CollectionConversionService;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of {@link RoleService}.
 */
@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CollectionConversionService collectionConversionService;

    @Override
    public List<RoleDTO> getRoles() {
        final List<RoleEntity> roles = roleRepository.findAll();

        log.debug("Found {} roles in Database.", roles.size());

        return collectionConversionService.convert(roles, RoleDTO.class);
    }

    @Override
    public Page<RoleEntity> getPageableRoles(final Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    @Override
    public RoleEntity findOrCreateRoleEntity(final String role) {

        log.debug("Find or create Role: {}", role);

        RoleEntity roleEntity = roleRepository.findByName(role);

        if (roleEntity == null) {
            log.debug("Role {} has not been found in database. Will be created.", role);
            roleEntity = roleRepository.save(RoleEntity.builder().name(role).build());
        }

        return roleEntity;
    }

}
