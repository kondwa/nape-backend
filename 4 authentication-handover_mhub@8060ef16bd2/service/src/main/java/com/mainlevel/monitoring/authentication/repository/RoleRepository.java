package com.mainlevel.monitoring.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mainlevel.monitoring.authentication.repository.entity.RoleEntity;

/**
 * DAO for roles.
 */
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    /**
     * Retrieves an entity by its role.
     * 
     * @param role must not be {@literal null}.
     * @return the entity or {@literal null} if none found
     * @throws IllegalArgumentException if role is {@literal null}
     */
    RoleEntity findByName(String role);

}
