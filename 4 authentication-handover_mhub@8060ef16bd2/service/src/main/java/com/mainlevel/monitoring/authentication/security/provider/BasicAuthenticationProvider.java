package com.mainlevel.monitoring.authentication.security.provider;

import static com.mainlevel.monitoring.common.constant.JWTConstant.ISSUER;
import static com.mainlevel.monitoring.common.constant.JWTConstant.PAYLOAD;
import static com.mainlevel.monitoring.common.constant.RoleConstant.ROLE_PREFIX;
import static io.jsonwebtoken.SignatureAlgorithm.RS512;
import static java.util.Calendar.HOUR_OF_DAY;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.springframework.transaction.annotation.Propagation.SUPPORTS;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mainlevel.monitoring.authentication.repository.UserRepository;
import com.mainlevel.monitoring.authentication.repository.entity.UserEntity;
import com.mainlevel.monitoring.authentication.service.PasswordService;
import com.mainlevel.monitoring.common.security.model.JWTAuthentication;
import com.mainlevel.monitoring.common.security.model.JWTPayload;
import com.mainlevel.monitoring.common.security.model.SimpleDeserializableGrantedAuthority;
import com.mainlevel.monitoring.common.service.KeyStoreService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.extern.slf4j.Slf4j;

/**
 * Provider caring about the basicAuthentication.
 */
@Slf4j
@Service("basicAuthenticationProvider")
public class BasicAuthenticationProvider implements AuthenticationProvider {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private KeyStoreService keyStoreService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordService passwordService;

    @Transactional(propagation = SUPPORTS, readOnly = true)
    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {

        try {

            if (!(authentication instanceof UsernamePasswordAuthenticationToken)) {
                return null;
            }

            final UsernamePasswordAuthenticationToken usernamePasswordAuthentication = (UsernamePasswordAuthenticationToken) authentication;
            final String username = usernamePasswordAuthentication.getPrincipal().toString();
            final String passwordToVerify = usernamePasswordAuthentication.getCredentials().toString();

            if (isBlank(passwordToVerify)) {
                return null;
            }

            final UserEntity userEntity = userRepository.findByUsername(username);

            boolean isValidPassword = false;

            if (userEntity != null) {
                isValidPassword = passwordService.verifyPassword(passwordToVerify, userEntity.getSalt(), userEntity.getPassword());
            }

            if (!isValidPassword) {
                throw new BadCredentialsException("Invalid credentials");
            }

            log.debug("Credentials for {} are valid.", username);

            final List<SimpleDeserializableGrantedAuthority> authorities = userEntity.getRoles().stream().map(
                r -> SimpleDeserializableGrantedAuthority.builder().authority(String.format("%s%s", ROLE_PREFIX, r.getName().toUpperCase())).build())
                .collect(Collectors.toList());

            final Calendar calendar = Calendar.getInstance();
            calendar.add(HOUR_OF_DAY, 10);

            final JWTAuthentication jwtAuthentication =
                JWTAuthentication.builder().name(username).authorities(authorities).authenticated(true).build();

            final JWTPayload jwtPayload = JWTPayload.builder().userId(userEntity.getId()).jwtAuthentication(jwtAuthentication).build();

            final String encryptedPayload = keyStoreService.encrypt(objectMapper.writeValueAsString(jwtPayload));

            final HashMap<String, Object> claimData = new HashMap<>();
            claimData.put(PAYLOAD, encryptedPayload);

            final Claims claims = new DefaultClaims(claimData).setIssuer(ISSUER).setSubject(username).setAudience(/* empty */ null)
                .setExpiration(calendar.getTime()).setNotBefore(/* empty */ null).setIssuedAt(new Date()).setId(/* empty */ null);

            final String jwt = Jwts.builder().setClaims(claims).signWith(RS512, keyStoreService.getPrivateKey()).compact();

            log.debug("Created JWT for user {}.", username);

            jwtAuthentication.setDetails(jwt);

            return jwtAuthentication;

        } catch (final AuthenticationException exception) {
            throw exception;
        } catch (final Exception exception) {
            throw new InternalAuthenticationServiceException(exception.getMessage(), exception);
        }
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return authentication == UsernamePasswordAuthenticationToken.class;
    }
}
