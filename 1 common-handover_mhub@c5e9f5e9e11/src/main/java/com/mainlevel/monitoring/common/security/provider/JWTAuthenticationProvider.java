package com.mainlevel.monitoring.common.security.provider;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.NonceExpiredException;
import org.springframework.stereotype.Service;

import com.mainlevel.monitoring.common.security.model.JWTAuthentication;
import com.mainlevel.monitoring.common.security.model.JWTFacade;
import com.mainlevel.monitoring.common.service.AuthenticationAccessService;

/**
 * Authentication provider that enables JWT authentication.
 */
@Service("jwtAuthenticationProvider")
public class JWTAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private AuthenticationAccessService authenticationAccessService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        try {

            if (!(authentication instanceof JWTAuthentication)) {
                return null;
            }

            final JWTAuthentication requestedJWTAuthentication = (JWTAuthentication) authentication;
            final String jwt = requestedJWTAuthentication.getDetails();

            final JWTFacade jwtFacade = authenticationAccessService.getJWTFacade(jwt);
            final JWTAuthentication jwtAuthentication = jwtFacade.getJwtPayload().getJwtAuthentication();

            final Date expiration = jwtFacade.getClaims().getExpiration();
            if (expiration != null) {
                if (Calendar.getInstance().getTime().after(expiration)) {
                    throw new NonceExpiredException("JWT expired");
                }
            }

            jwtAuthentication.setDetails(jwt);

            return jwtAuthentication;

        } catch (AuthenticationException exception) {

            throw exception;

        } catch (Exception exception) {

            throw new InternalAuthenticationServiceException(exception.getMessage(), exception);

        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication == JWTAuthentication.class;
    }
}
