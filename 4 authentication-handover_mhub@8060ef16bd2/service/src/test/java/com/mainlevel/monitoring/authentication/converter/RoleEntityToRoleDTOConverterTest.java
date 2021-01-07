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
import com.mainlevel.monitoring.authentication.repository.entity.RoleEntity;
import com.mainlevel.monitoring.common.constant.RoleConstant;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;
import com.mainlevel.monitoring.common.service.CollectionConversionService;

@SuppressWarnings("javadoc")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RoleEntityToRoleDTOConverterTest.TestConfiguration.class)
@TestPropertySource(properties = {"spring.cloud.config.failFast : false"})
public class RoleEntityToRoleDTOConverterTest extends AbstractConverterTest {

    public static class TestConfiguration {
        @Bean
        public RoleEntityToRoleDTOConverter converter() {
            return new RoleEntityToRoleDTOConverter();
        }

        @Bean
        public CollectionConversionService collectionConversionService() {
            return mock(CollectionConversionService.class);
        }
    }

    @Autowired
    private RoleEntityToRoleDTOConverter converter;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    @Override
    public void testComponentAnnotation() {
        assertNotNull(RoleEntityToRoleDTOConverter.class.getAnnotation(Component.class));
    }

    @Test
    @Override
    public void testSuperClass() {
        assertEquals(AbstractApplicationAwareConverter.class, RoleEntityToRoleDTOConverter.class.getSuperclass());
    }

    @Test
    @Override
    public void testConvertWithAllData() {
        final RoleEntity roleEntity = createRole();
        final RoleDTO roleDTO = converter.convert(roleEntity);

        assertNotNull(roleDTO);
        assertEquals(roleEntity.getId(), roleDTO.getIdentifier());
        assertEquals(roleEntity.getName(), roleDTO.getName());
    }

    @Test
    @Override
    public void testConvertWithEmpty() {
        final RoleDTO roleDTO = converter.convert(new RoleEntity());

        assertNotNull(roleDTO);
        assertNull(roleDTO.getIdentifier());
        assertNull(roleDTO.getName());
    }

    private RoleEntity createRole() {
        return RoleEntity.builder().id(1L).name(RoleConstant.ROLE_USER).build();
    }
}
