package com.mainlevel.monitoring.authentication.service.impl;

import static com.mainlevel.monitoring.common.constant.JWTConstant.ISSUER;
import static com.mainlevel.monitoring.common.constant.RoleConstant.ROLE_PREFIX;
import static io.jsonwebtoken.SignatureAlgorithm.RS512;
import static java.util.stream.Collectors.toList;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mainlevel.monitoring.authentication.api.dto.UserDTO;
import com.mainlevel.monitoring.authentication.repository.JwtRepository;
import com.mainlevel.monitoring.authentication.repository.entity.JwtEntity;
import com.mainlevel.monitoring.authentication.repository.entity.UserEntity;
import com.mainlevel.monitoring.authentication.service.JWTService;
import com.mainlevel.monitoring.common.constant.JWTConstant;
import com.mainlevel.monitoring.common.security.model.JWTAuthentication;
import com.mainlevel.monitoring.common.security.model.JWTPayload;
import com.mainlevel.monitoring.common.security.model.SimpleDeserializableGrantedAuthority;
import com.mainlevel.monitoring.common.service.KeyStoreService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of {@link JWTService}.
 */
@Slf4j
@Service
public class JWTServiceImpl implements JWTService {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private KeyStoreService keyStoreService;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private JwtRepository jwtRepository;

    @Override
    public Authentication getJWTAuthenticationForUser(final UserEntity user, final boolean authenticated) {
        return createJWTAuthentication(user, authenticated, null);
    }

    @Override
    public Authentication getJWTAuthenticationForUser(final UserDTO user, final boolean authenticated, final long validityInHours) {
        return createJWTAuthentication(conversionService.convert(user, UserEntity.class), authenticated, validityInHours);
    }

    @Override
    public JwtEntity getJwtEntity(final Long id) {
        return jwtRepository.findOne(id);
    }

    @Override
    public JwtEntity getJwtEntityByJwt(final String jwt) {
        return jwtRepository.findByJwt(jwt);
    }

    @Override
    public String getJWTForUserAsString(final UserDTO user) {
        return (String) getJWTAuthenticationForUser(user, true, 10).getDetails();
    }

    private Authentication createJWTAuthentication(final UserEntity user, final boolean authenticated, final Long validityInHours) {
        try {
            final JWTAuthentication jwtAuthentication =
                JWTAuthentication.builder().name(user.getUsername()).authorities(getAuthoritiesForUser(user)).authenticated(authenticated).build();

            final JWTPayload jwtPayload = JWTPayload.builder().userId(user.getId()).jwtAuthentication(jwtAuthentication).build();

            final String encryptedPayload = keyStoreService.encrypt(objectMapper.writeValueAsString(jwtPayload));

            final HashMap<String, Object> claimData = new HashMap<>();
            claimData.put(JWTConstant.PAYLOAD, encryptedPayload);

            Date validityDate = null;
            if (validityInHours != null) {
                final LocalDateTime now = LocalDateTime.now();
                final LocalDateTime validityDateTime = now.plusHours(validityInHours);
                validityDate = Date.from(validityDateTime.atZone(ZoneId.systemDefault()).toInstant());
            }

            final Claims claims = new DefaultClaims(claimData).setIssuer(ISSUER).setSubject(user.getUsername()).setAudience(null)
                .setExpiration(validityDate).setNotBefore(null).setIssuedAt(new Date()).setId(null);

            final String jwt = Jwts.builder().setClaims(claims).signWith(RS512, keyStoreService.getPrivateKey()).compact();

            jwtAuthentication.setDetails(jwt);

            return jwtAuthentication;
        } catch (final JsonProcessingException exception) {

            log.error("Creating JWT Authentication failed for user: {}. Message: {}", user.getUsername(), exception.getMessage());
            log.debug("Stacktrace: {}", (Object[]) exception.getStackTrace());

            throw new RuntimeException(exception);
        }
    }

    private List<SimpleDeserializableGrantedAuthority> getAuthoritiesForUser(final UserEntity user) {
        return user.getRoles().stream()
            .map(r -> SimpleDeserializableGrantedAuthority.builder().authority(String.format("%s%s", ROLE_PREFIX, r.getName().toUpperCase())).build())
            .collect(toList());
    }
}
