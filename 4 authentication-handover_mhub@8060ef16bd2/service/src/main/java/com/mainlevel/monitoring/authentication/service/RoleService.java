package com.mainlevel.monitoring.authentication.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mainlevel.monitoring.authentication.api.dto.RoleDTO;
import com.mainlevel.monitoring.authentication.repository.entity.RoleEntity;

/**
 * Service for maintaining roles.
 */
public interface RoleService {

    /**
     * Get all possible Roles.
     *
     * @return A List with all available roles.
     */
    List<RoleDTO> getRoles();

    /**
     * Retrieves the list of roles for pagination.
     *
     * @param pageable the pageable
     * @return the page
     */
    Page<RoleEntity> getPageableRoles(Pageable pageable);

    /**
     * Creates a new {@link RoleEntity} for the given role name. If the role is not present in the database, a new entry will be created.
     *
     * @param role the name of the role
     * @return the created {@link RoleEntity}
     */
    RoleEntity findOrCreateRoleEntity(final String role);

}
