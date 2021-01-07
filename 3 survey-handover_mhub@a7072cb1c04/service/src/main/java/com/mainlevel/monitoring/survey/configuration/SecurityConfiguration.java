package com.mainlevel.monitoring.survey.configuration;

import static com.mainlevel.monitoring.common.constant.ProfileConstant.WITH_SECURITY;
import static com.mainlevel.monitoring.common.constant.RoleConstant.ROLE_ANONYMOUS;
import static org.springframework.http.HttpMethod.OPTIONS;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.RememberMeServices;

import com.mainlevel.monitoring.common.configuration.annotation.EnableSecurityServices;

/**
 * Security configuration for this application
 */
@Profile(WITH_SECURITY)
@EnableSecurityServices
@EnableGlobalMethodSecurity(securedEnabled = true)
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Resource(name = "jwtAuthenticationProvider")
    private AuthenticationProvider jwtAuthenticationProvider;

    @Autowired
    private RememberMeServices rememberMeService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(jwtAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //@formatter:off
		http.
			httpBasic()
				.realmName("Mainlevel Survey")
			.and()
				.csrf().disable()
				.headers()
			.and()
				.sessionManagement().sessionCreationPolicy(STATELESS)
			.and()
				.rememberMe().rememberMeServices(rememberMeService)
            .and()
                .anonymous().authorities(ROLE_ANONYMOUS)
			.and()
				.authorizeRequests()
					.antMatchers(OPTIONS).permitAll()
					.anyRequest().authenticated();
		//@formatter:on
    }
}
