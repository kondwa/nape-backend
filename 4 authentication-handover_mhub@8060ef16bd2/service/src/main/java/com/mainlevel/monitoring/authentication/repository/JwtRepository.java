package com.mainlevel.monitoring.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mainlevel.monitoring.authentication.repository.entity.JwtEntity;
import com.mainlevel.monitoring.authentication.repository.entity.UserEntity;

/**
 * Repository for maintaining JWT entities.
 */
public interface JwtRepository extends JpaRepository<JwtEntity, Long> {

    /**
     * Retrieves an entity by the related {@link UserEntity}.
     *
     * @param userEntity must not be {@literal null}.
     * @return the entity or {@literal null} if none found
     * @throws IllegalArgumentException if {@link UserEntity} is {@literal null}
     */
    JwtEntity findByUserEntity(UserEntity userEntity);

    /**
     * Retrieves an entity by the given JWT value.
     *
     * @param jwt must not be {@literal null}.
     * @return the entity or {@literal null} if none found
     * @throws IllegalArgumentException if jwt is {@literal null}
     */
    JwtEntity findByJwt(String jwt);
}
