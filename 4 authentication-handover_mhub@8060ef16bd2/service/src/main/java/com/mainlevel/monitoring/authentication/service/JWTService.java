package com.mainlevel.monitoring.authentication.service;

import org.springframework.security.core.Authentication;

import com.mainlevel.monitoring.authentication.api.dto.UserDTO;
import com.mainlevel.monitoring.authentication.repository.entity.JwtEntity;
import com.mainlevel.monitoring.authentication.repository.entity.UserEntity;

/**
 * Service for interacting with JWT objects.
 */
public interface JWTService {

    /**
     * Create an authentication for the given temporary user
     *
     * @param user the {@link UserEntity} entity the authentication should be created for
     * @param authenticated flag which indicates, if the temporary user is authenticated
     * @return the {@link Authentication}
     */
    Authentication getJWTAuthenticationForUser(UserEntity user, boolean authenticated);

    /**
     * Create an authentication for the given temporary user
     *
     * @param user the {@link UserEntity} entity the authentication should be created for
     * @param authenticated flag which indicates, if the temporary user is authenticated
     * @param validityInHours the validity for the new JWT in hours
     * @return the {@link Authentication}
     */
    Authentication getJWTAuthenticationForUser(UserDTO user, boolean authenticated, long validityInHours);

    /**
     * Retrieve the JWT for the given user.
     *
     * @param user the user
     * @return the JWT as string
     */
    String getJWTForUserAsString(UserDTO user);

    /**
     * Load a {@link JwtEntity} from persistence layer
     *
     * @param id the id of the entity
     * @return the entity or <code>null</code> if not found
     */
    JwtEntity getJwtEntity(Long id);

    /**
     * Load a {@link JwtEntity} by the given token
     *
     * @param jwt the token of the entity
     * @return the entity or <code>null</code> if not found
     */
    JwtEntity getJwtEntityByJwt(String jwt);
}
