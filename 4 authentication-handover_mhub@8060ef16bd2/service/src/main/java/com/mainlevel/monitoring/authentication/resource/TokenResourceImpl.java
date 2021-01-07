package com.mainlevel.monitoring.authentication.resource;

import static com.mainlevel.monitoring.common.constant.RoleConstant.ROLE_USER;
import static java.lang.String.valueOf;
import static org.springframework.http.HttpStatus.OK;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RestController;

import com.mainlevel.monitoring.authentication.api.dto.TokenDTO;
import com.mainlevel.monitoring.authentication.api.resource.TokenResource;
import com.mainlevel.monitoring.common.monitoring.annotation.PerformanceMonitor;
import com.mainlevel.monitoring.common.service.AuthenticationAccessService;

import lombok.extern.slf4j.Slf4j;

/**
 * Endpoint for receiving the token.
 */
@Slf4j
@RestController
public class TokenResourceImpl implements TokenResource {

    @Autowired
    private AuthenticationAccessService authenticationAccessService;

    @Override
    @PerformanceMonitor
    public ResponseEntity<TokenDTO> doLogin() {

        String userId = valueOf(authenticationAccessService.getCurrentUserId());
        String userName = authenticationAccessService.getCurrentUsername();
        String details = (String) authenticationAccessService.getAuthentication().getDetails();

        final TokenDTO tokenDTO = TokenDTO.builder().user(userId).userName(userName).token(details).build();

        log.info("{} gets his daily dose of JWT.", userName);

        return new ResponseEntity<>(tokenDTO, OK);
    }

    @Override
    @Secured(ROLE_USER)
    @PerformanceMonitor
    public ResponseEntity<Void> checkIfTokenIsValid() {
        // No manual check here. It will be done automatically within the JWTAuthenticationProvider.
        return ResponseEntity.ok().build();
    }

}
