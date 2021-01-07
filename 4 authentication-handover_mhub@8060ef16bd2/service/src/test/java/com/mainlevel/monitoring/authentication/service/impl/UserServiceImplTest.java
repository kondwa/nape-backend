package com.mainlevel.monitoring.authentication.service.impl;

import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.mainlevel.monitoring.authentication.repository.UserRepository;
import com.mainlevel.monitoring.authentication.service.PasswordService;
import com.mainlevel.monitoring.authentication.service.RoleService;
import com.mainlevel.monitoring.authentication.service.UserService;
import com.mainlevel.monitoring.common.service.CollectionConversionService;

@SuppressWarnings("javadoc")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {UserServiceImplTest.TestConfiguration.class}, loader = AnnotationConfigContextLoader.class)
public class UserServiceImplTest {

    public static class TestConfiguration {

        @Bean
        private UserService userService() {
            return new UserServiceImpl();
        }

        @Bean
        private RoleService roleService() {
            return mock(RoleService.class);
        }

        @Bean
        private UserRepository userRepository() {
            return mock(UserRepository.class);
        }

        @Bean
        private ConversionService conversionService() {
            return mock(ConversionService.class);
        }

        @Bean
        private CollectionConversionService collectionConversionService() {
            return mock(CollectionConversionService.class);
        }

        @Bean
        private PasswordService passwordService() {
            return mock(PasswordService.class);
        }

    }

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ConversionService conversionService;

    @Autowired
    PasswordService passwordService;

    @Test
    public void testCreateUser() {

    }

}
