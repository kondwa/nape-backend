package com.mainlevel.monitoring.authentication.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;

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
import com.mainlevel.monitoring.authentication.converter.RoleDTOToRoleEntityConverter;
import com.mainlevel.monitoring.authentication.repository.entity.RoleEntity;
import com.mainlevel.monitoring.common.constant.RoleConstant;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.common.service.CollectionConversionService;

@SuppressWarnings("javadoc")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RoleDTOToRoleEntityConverterTest.TestConfiguration.class)
@TestPropertySource(properties = {"spring.cloud.config.failFast : false"})
public class RoleDTOToRoleEntityConverterTest extends AbstractConverterTest {

    public static class TestConfiguration {
        @Bean
        public RoleDTOToRoleEntityConverter converter() {
            return new RoleDTOToRoleEntityConverter();
        }

        @Bean
        public CollectionConversionService collectionConversionService() {
            return mock(CollectionConversionService.class);
        }
    }

    @Autowired
    private RoleDTOToRoleEntityConverter converter;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    @Override
    public void testComponentAnnotation() {
        assertNotNull(RoleDTOToRoleEntityConverter.class.getAnnotation(Component.class));
    }

    @Test
    @Override
    public void testSuperClass() {
        assertEquals(AbstractApplicationAwareConverter.class, RoleDTOToRoleEntityConverter.class.getSuperclass());
    }

    @Test
    @Override
    public void testConvertWithAllData() {
        final RoleDTO roleDTO = createRole();
        final RoleEntity roleEntity = converter.convert(roleDTO);

        assertNotNull(roleEntity);
        assertEquals(roleEntity.getId(), roleDTO.getIdentifier());
        assertEquals(roleEntity.getName(), roleDTO.getName());
    }

    @Test
    @Override
    public void testConvertWithEmpty() {
        final RoleEntity roleEntity = converter.convert(new RoleDTO());

        assertNotNull(roleEntity);
        assertNull(roleEntity.getId());
        assertNull(roleEntity.getName());
    }

    private RoleDTO createRole() {
        return RoleDTO.builder().identifier(1L).name(RoleConstant.ROLE_USER).build();
    }
}
