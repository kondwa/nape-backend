package com.mainlevel.monitoring.common.security.provider;

import static com.mainlevel.monitoring.common.constant.HTTPConstant.AUTHORIZATION;
import static com.mainlevel.monitoring.common.constant.HTTPConstant.BEARER;
import static org.apache.commons.lang3.StringUtils.isBlank;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.stereotype.Service;

import com.mainlevel.monitoring.common.security.model.JWTAuthentication;
import com.mainlevel.monitoring.common.security.model.JWTFacade;
import com.mainlevel.monitoring.common.service.AuthenticationAccessService;

import lombok.extern.slf4j.Slf4j;

/**
 * RememberMeService that automatically logs in a user with JWT.
 */
@Slf4j
@Service
public class JWTRememberMeProvider implements RememberMeServices {

    @Autowired
    private AuthenticationAccessService authenticationAccessService;

    @Override
    public Authentication autoLogin(HttpServletRequest request, HttpServletResponse response) {
        try {
            String authorizationHeader = request.getHeader(AUTHORIZATION);

            if (isBlank(authorizationHeader)) {
                return null;
            }
            String jwt = authorizationHeader.substring(String.format("%s ", BEARER).length());

            if (isBlank(jwt)) {
                return null;
            }
            JWTFacade jwtFacade = authenticationAccessService.getJWTFacade(jwt);
            JWTAuthentication jwtAuthentication = jwtFacade.getJwtPayload().getJwtAuthentication();

            jwtAuthentication.setDetails(jwt);

            return jwtAuthentication;

        } catch (Exception exception) {

            log.warn(exception.getMessage());

        }

        return null;
    }

    @Override
    public void loginFail(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    public void loginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication successfulAuthentication) {

    }
}
