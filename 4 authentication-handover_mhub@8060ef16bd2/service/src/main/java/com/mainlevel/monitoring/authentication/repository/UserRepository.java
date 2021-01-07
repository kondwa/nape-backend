package com.mainlevel.monitoring.authentication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mainlevel.monitoring.authentication.repository.entity.RoleEntity;
import com.mainlevel.monitoring.authentication.repository.entity.UserEntity;

/**
 * DAO for users.
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Retrieves an entity by its user name.
     * 
     * @param username must not be {@literal null}.
     * @return the entity or {@literal null} if none found
     * @throws IllegalArgumentException if user name is {@literal null}
     */
    UserEntity findByUsername(String username);

    /**
     * Retrieves an entity by the related id and {@link RoleEntity}.
     * 
     * @param id must not be {@literal null}.
     * @param roleEntity must not be {@literal null}.
     * @return the entity or {@literal null} if none found
     * @throws IllegalArgumentException if one of the given parameter is {@literal null}
     */
    UserEntity findByIdAndRoles(Long id, RoleEntity roleEntity);

    /**
     * Retrieves an entity by the related {@link RoleEntity}.
     * 
     * @param roleEntity must not be {@literal null}.
     * @return the entity or {@literal null} if none found
     * @throws IllegalArgumentException if {@link RoleEntity} is {@literal null}
     */
    List<UserEntity> findByRoles(RoleEntity roleEntity);

}
