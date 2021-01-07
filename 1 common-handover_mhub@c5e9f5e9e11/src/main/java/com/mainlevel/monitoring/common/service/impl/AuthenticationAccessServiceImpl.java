package com.mainlevel.monitoring.common.service.impl;

import static com.mainlevel.monitoring.common.constant.HTTPConstant.BEARER;

import java.util.Collection;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mainlevel.monitoring.common.constant.JWTConstant;
import com.mainlevel.monitoring.common.security.model.JWTAuthentication;
import com.mainlevel.monitoring.common.security.model.JWTFacade;
import com.mainlevel.monitoring.common.security.model.JWTPayload;
import com.mainlevel.monitoring.common.service.AuthenticationAccessService;
import com.mainlevel.monitoring.common.service.KeyStoreService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of {@link AuthenticationAccessService}.
 */
@Slf4j
@Service
public class AuthenticationAccessServiceImpl implements AuthenticationAccessService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private KeyStoreService keyStoreService;

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public JWTFacade getJWTFacade(final String jwt) {
        final Claims claims = (Claims) Jwts.parser().setSigningKey(keyStoreService.getPrivateKey()).parse(jwt).getBody();
        final String base46EncryptedPayload = (String) claims.get(JWTConstant.PAYLOAD);
        final String unencryptedPayload = keyStoreService.decrypt(base46EncryptedPayload);

        try {
            final JWTPayload jwtPayload = objectMapper.readValue(unencryptedPayload, JWTPayload.class);
            return JWTFacade.builder().claims(claims).jwtPayload(jwtPayload).build();
        } catch (final Exception exception) {
            throw new RuntimeException(exception.getMessage(), exception);
        }
    }

    @Override
    public long getCurrentUserId() {
        final Authentication authentication = getAuthentication();
        final JWTFacade jwtFacade = getJWTFacade(authentication.getDetails().toString());
        final long userId = jwtFacade.getJwtPayload().getUserId();
        return userId;
    }

    @Override
    public boolean hasRole(final String role) {

        if (role == null) {
            return false;
        }

        final Authentication authentication = getAuthentication();
        final Collection<? extends GrantedAuthority> authorities;

        if ((authentication != null) && ((authorities = authentication.getAuthorities()) != null)) {
            return authorities.parallelStream().anyMatch(grantedAuthority -> Objects.equals(grantedAuthority.getAuthority(), role));
        }
        return false;
    }

    @Override
    public String getCurrentAccessToken() {
        return String.format("%s %s", BEARER, getAuthentication().getDetails());

    }

    @Override
    public String getCurrentUsername() {

        String currentUsername = "Unknown (No Authentication)";

        final Authentication authentication = getAuthentication();

        if (authentication == null) {
            log.warn("Asked in a non-security context for a username");
        } else if (authentication instanceof UsernamePasswordAuthenticationToken) {
            currentUsername = authentication.getPrincipal().toString();
        } else if (authentication instanceof JWTAuthentication) {
            currentUsername = ((JWTAuthentication) authentication).getName();
        }

        return currentUsername;
    }
}
