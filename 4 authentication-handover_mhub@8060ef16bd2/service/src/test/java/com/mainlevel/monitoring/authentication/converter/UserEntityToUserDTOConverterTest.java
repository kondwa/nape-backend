package com.mainlevel.monitoring.authentication.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mainlevel.monitoring.authentication.api.dto.RoleDTO;
import com.mainlevel.monitoring.authentication.api.dto.UserDTO;
import com.mainlevel.monitoring.authentication.repository.entity.RoleEntity;
import com.mainlevel.monitoring.authentication.repository.entity.UserEntity;
import com.mainlevel.monitoring.common.constant.RoleConstant;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.common.service.CollectionConversionService;

@SuppressWarnings("javadoc")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = UserEntityToUserDTOConverterTest.TestConfiguration.class)
@TestPropertySource(properties = {"spring.cloud.config.failFast : false"})
public class UserEntityToUserDTOConverterTest extends AbstractConverterTest {

    public static class TestConfiguration {
        @Bean
        public UserEntityToUserDTOConverter converter() {
            return new UserEntityToUserDTOConverter();
        }

        @Bean
        public CollectionConversionService collectionConversionService() {
            return mock(CollectionConversionService.class);
        }
    }

    @Autowired
    private UserEntityToUserDTOConverter converter;

    @Autowired
    private CollectionConversionService collectionConversionService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    @Override
    public void testComponentAnnotation() {
        assertNotNull(UserEntityToUserDTOConverter.class.getAnnotation(Component.class));
    }

    @Test
    @Override
    public void testSuperClass() {
        assertEquals(AbstractApplicationAwareConverter.class, UserEntityToUserDTOConverter.class.getSuperclass());
    }

    @Test
    @Override
    public void testConvertWithAllData() {
        final List<RoleDTO> roleDTOs = Collections.singletonList(RoleDTO.builder().identifier(1L).name(RoleConstant.ROLE_TEMPLATE_VIEWER).build());
        when(collectionConversionService.convert(anyCollectionOf(RoleEntity.class), eq(RoleDTO.class))).thenReturn(roleDTOs);

        final UserEntity user = this.createUser();
        final UserDTO userDTO = converter.convert(user);

        assertNotNull(userDTO);
        assertEquals(user.getId(), userDTO.getIdentifier());
        assertEquals(user.getUsername(), userDTO.getUsername());
        assertEquals(user.getFirstname(), userDTO.getFirstName());
        assertEquals(user.getLastname(), userDTO.getLastName());

        assertNotNull(user.getRoles());
        assertEquals(user.getRoles().size(), userDTO.getRoles().size());
    }

    @Test
    @Override
    public void testConvertWithEmpty() {
        when(collectionConversionService.convert(anyCollectionOf(RoleEntity.class), eq(RoleDTO.class))).thenReturn(null);

        final UserDTO userDTO = converter.convert(new UserEntity());

        assertNotNull(userDTO);
        assertNull(userDTO.getIdentifier());
        assertNull(userDTO.getUsername());
        assertNull(userDTO.getFirstName());
        assertNull(userDTO.getLastName());
        assertNull(userDTO.getRoles());
    }

    private UserEntity createUser() {
        final List<RoleEntity> roles = Collections.singletonList(RoleEntity.builder().id(1L).name(RoleConstant.ROLE_TEMPLATE_VIEWER).build());
        return UserEntity.builder().id(1L).username("Test@prodyna.com").firstname("Hans").lastname("Mustermann").salt("salt").password("password")
            .roles(roles).build();
    }
}
