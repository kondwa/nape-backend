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
@SpringBootTest(classes = UserDTOToUserEntityConverterTest.TestConfiguration.class)
@TestPropertySource(properties = {"spring.cloud.config.failFast : false"})
public class UserDTOToUserEntityConverterTest extends AbstractConverterTest {

    public static class TestConfiguration {
        @Bean
        public UserDTOToUserEntityConverter converter() {
            return new UserDTOToUserEntityConverter();
        }

        @Bean
        public CollectionConversionService collectionConversionService() {
            return mock(CollectionConversionService.class);
        }
    }

    @Autowired
    private UserDTOToUserEntityConverter converter;

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
        assertNotNull(UserDTOToUserEntityConverter.class.getAnnotation(Component.class));
    }

    @Test
    @Override
    public void testSuperClass() {
        assertEquals(AbstractApplicationAwareConverter.class, UserDTOToUserEntityConverter.class.getSuperclass());
    }

    @Test
    @Override
    public void testConvertWithAllData() {
        final List<RoleEntity> roleEntities = Collections.singletonList(RoleEntity.builder().id(1L).name(RoleConstant.ROLE_TEMPLATE_VIEWER).build());
        when(collectionConversionService.convert(anyCollectionOf(RoleDTO.class), eq(RoleEntity.class))).thenReturn(roleEntities);

        final UserDTO userDTO = this.createUser();
        final UserEntity user = converter.convert(userDTO);

        assertNotNull(user);
        assertEquals(user.getId(), userDTO.getIdentifier());
        assertEquals(user.getUsername(), userDTO.getUsername());
        assertEquals(user.getFirstname(), userDTO.getFirstName());
        assertEquals(user.getLastname(), userDTO.getLastName());
        assertEquals(user.getRoles().size(), userDTO.getRoles().size());

    }

    @Test
    @Override
    public void testConvertWithEmpty() {

        when(collectionConversionService.convert(anyCollectionOf(RoleDTO.class), eq(RoleEntity.class))).thenReturn(null);

        final UserEntity user = converter.convert(new UserDTO());
        assertNotNull(user);
        assertNull(user.getId());
        assertNull(user.getUsername());
        assertNull(user.getFirstname());
        assertNull(user.getLastname());
        assertNull(user.getRoles());
    }

    private UserDTO createUser() {
        final List<RoleDTO> roles = Collections.singletonList(RoleDTO.builder().identifier(1L).name(RoleConstant.ROLE_TEMPLATE_VIEWER).build());
        return UserDTO.builder().identifier(1L).username("Test@prodyna.com").firstName("Hans").lastName("Mustermann").password("password")
            .roles(roles).build();
    }
}
