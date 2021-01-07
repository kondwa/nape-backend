package com.mainlevel.monitoring.common.service.impl;

import static com.mainlevel.monitoring.common.constant.ProfileConstant.WITHOUT_SECURITY;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.mainlevel.monitoring.common.security.model.JWTAuthentication;
import com.mainlevel.monitoring.common.service.KeyStoreService;
import com.mainlevel.monitoring.common.service.impl.AuthenticationAccessServiceImpl;

@SuppressWarnings("javadoc")
@ActiveProfiles(WITHOUT_SECURITY)
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AuthenticationAccessServiceImplTest.TestConfiguration.class}, loader = AnnotationConfigWebContextLoader.class)
public class AuthenticationAccessServiceImplTest {

    public static class TestConfiguration {

        @Bean
        public KeyStoreService keyStoreService() {
            return mock(KeyStoreService.class);
        }

        @Bean
        public AuthenticationAccessServiceImpl accessServiceImpl() {
            return spy(AuthenticationAccessServiceImpl.class);
        }

    }

    @Autowired
    private AuthenticationAccessServiceImpl accessServiceImpl;

    /**
     * Test of getCurrentUsername method, of class AuthenticationAccessServiceImpl.
     */
    @Test
    public void testGetCurrentUsername() {
        doReturn(JWTAuthentication.builder().name("theUser").build()).when(accessServiceImpl).getAuthentication();
        final String result = accessServiceImpl.getCurrentUsername();
        assertEquals("theUser", result);
    }

}