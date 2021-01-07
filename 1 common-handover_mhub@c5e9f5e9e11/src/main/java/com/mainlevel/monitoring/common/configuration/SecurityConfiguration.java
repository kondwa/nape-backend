package com.mainlevel.monitoring.common.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.web.authentication.RememberMeServices;

import com.mainlevel.monitoring.common.security.provider.JWTAuthenticationProvider;
import com.mainlevel.monitoring.common.security.provider.JWTRememberMeProvider;
import com.mainlevel.monitoring.common.service.AuthenticationAccessService;
import com.mainlevel.monitoring.common.service.KeyStoreService;
import com.mainlevel.monitoring.common.service.impl.AuthenticationAccessServiceImpl;
import com.mainlevel.monitoring.common.service.impl.KeyStoreServiceImpl;

/**
 * Common security configuration.
 */
@Configuration
public class SecurityConfiguration {

    /**
     * Provides the keystore service.
     *
     * @return the bean
     */
    @Bean
    public KeyStoreService keyStoreService() {
        return new KeyStoreServiceImpl();
    }

    /**
     * Provides the authentication provider.
     *
     * @return the bean
     */
    @Bean
    public AuthenticationProvider jwtAuthenticationProvider() {
        return new JWTAuthenticationProvider();
    }

    /**
     * Provides the JWT remember me provider.
     *
     * @return the bean
     */
    @Bean
    public RememberMeServices jwtRememberMeProvider() {
        return new JWTRememberMeProvider();
    }

    /**
     * Provides the authentication access service.
     *
     * @return the bean
     */
    @Bean
    public AuthenticationAccessService authenticationAccessService() {
        return new AuthenticationAccessServiceImpl();
    }
}
