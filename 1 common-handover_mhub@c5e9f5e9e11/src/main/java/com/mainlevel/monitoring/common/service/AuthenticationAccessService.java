package com.mainlevel.monitoring.common.service;

import org.springframework.security.core.Authentication;

import com.mainlevel.monitoring.common.constant.RoleConstant;
import com.mainlevel.monitoring.common.security.model.JWTFacade;

/**
 * Common service that enables access to authentication information of the current request.
 */
public interface AuthenticationAccessService {

    /**
     * Provides the current {@link Authentication}.
     *
     * @return {@link Authentication} or {@code null} if no authentication information is available.
     */
    Authentication getAuthentication();

    /**
     * Returns a {@link JWTFacade} that aggregates the jwt claims and payload.
     *
     * @param jwt the jwt the build the {@link JWTFacade} for.
     * @return {@link JWTFacade}
     */
    JWTFacade getJWTFacade(final String jwt);

    /**
     * Checks if the current {@link Authentication} has the provided {@link RoleConstant}
     *
     * @param role The Role to check for. Possible Roles can be found in {@link RoleConstant}.
     * @return true if the given role is assigned to the current user
     */
    boolean hasRole(final String role);

    /**
     * Shortcut for {@link JWTFacade#getJwtPayload()}.getUserId().
     *
     * @return the userId of the current user
     */
    long getCurrentUserId();

    /**
     * Returns access token for the current user, with the prefix 'Bearer '
     *
     * @return the access token
     */
    String getCurrentAccessToken();

    /**
     * Return user name of current user.
     *
     * @return the username
     */
    String getCurrentUsername();

}
